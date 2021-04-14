package com.vote.sessoes.api.service.sessaoVotacao;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Pauta;
import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.SimNaoEnum;
import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.TotalVotoOpcao;
import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Voto;
import com.vote.sessoes.api.dto.sessaoVotacao.ResultadoVotacaoDto;
import com.vote.sessoes.api.exception.CustomValidationException;
import com.vote.sessoes.api.exception.NotFoundException;
import com.vote.sessoes.api.exception.UnprocessableException;
import com.vote.sessoes.api.infrastructure.service.ValidaCPF;
import com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository.VotoRepository;

@Service
public class VotoService {

	Logger log = LoggerFactory.getLogger(VotoService.class);

	private VotoRepository votoRepository;
	private AssociadoService associadoService;
	private PautaService pautaService;
	private KafkaService<ResultadoVotacaoDto> kafkaService;

	@Value("${votesessoes.resultadovotacao.kafka.topic}")
	private String topicoKafka;

	public VotoService(VotoRepository votoRepository, AssociadoService associadoService, PautaService pautaService,
			KafkaService<ResultadoVotacaoDto> kafkaService) {
		this.votoRepository = votoRepository;
		this.associadoService = associadoService;
		this.pautaService = pautaService;
		this.kafkaService = kafkaService;
	}

	public Voto votar(String pautaId, String cpfAssociado, SimNaoEnum opcao) {
		if (cpfAssociado == null || cpfAssociado.isBlank() && !ValidaCPF.isCPF(cpfAssociado)) {
			throw new CustomValidationException("cpf.invalido");
		}

		cpfAssociado = cpfAssociado.replaceAll("[^0-9]", "");

		if (associadoService.findAvalibleById(cpfAssociado) == null) {
			throw new NotFoundException("associado.nao.encontrado");
		}

		if (pautaService.buscarPorIdDisponivelVotacao(pautaId) == null) {
			throw new UnprocessableException("pauta.finalizada");
		}

		if (votoRepository.findByPautaIdAndCpf(pautaId, cpfAssociado) != null) {
			throw new UnprocessableException("votacao.indisponivel.cpf");
		}

		if (!verifyCPF(cpfAssociado)) {
			throw new UnprocessableException("votacao.indisponivel.cpf");
		}

		return votoRepository.save(new Voto(pautaId, cpfAssociado, opcao, LocalDateTime.now(ZoneOffset.UTC)));
	}

	private boolean verifyCPF(String cpf) {
		RestTemplate restTemplate = new RestTemplate();
		String resposta = restTemplate.getForObject("https://user-info.herokuapp.com/users/" + cpf, String.class);
		if (resposta.contains("ABLE_TO_VOTE")) {
			return true;
		}
		return false;
	}

	public ResultadoVotacaoDto calcularResultadoVotacao(String pautaId) {
		Pauta pauta = pautaService.buscarPorId(pautaId);

		if (pauta == null) {
			throw new NotFoundException("pauta.nao.encontrada");
		}

		if (pauta.getSessao() == null || pauta.getSessao().getDataHoraFim() == null) {
			throw new NotFoundException("sessao.nao.encontrada");
		}

		if (pauta.getSessao().getDataHoraFim().isAfter(LocalDateTime.now(ZoneOffset.UTC))) {
			throw new UnprocessableException("pauta.nao.encontrada");
		}

		if (pauta.isResultadoApurado()) {
			return new ResultadoVotacaoDto(pautaId, pauta.getTotalVotos());
		}

		return processarResultado(pauta);
	}

	public ResultadoVotacaoDto processarResultado(Pauta pauta) {
		log.info("Processando votos da pauta {}", pauta.getId());

		ResultadoVotacaoDto result = null;

		AggregationResults<TotalVotoOpcao> resultado = votoRepository.calcularResultadoVotacao(pauta.getId());
		result = new ResultadoVotacaoDto(pauta.getId(), resultado.getMappedResults());

		if (result.getTotalVotos() == null || result.getTotalVotos().isEmpty()) {
			throw new RuntimeException("Erro.contabilizacao");
		}

		pauta.setTotalVotos(result.getTotalVotos());
		pauta.setResultadoApurado(true);

		pautaService.gravar(pauta);

		kafkaService.send(topicoKafka, result).addCallback(
				sucesso -> log.info("Mensagem enviada para o topico '{}' com sucesso {}", topicoKafka, sucesso),
				erro -> log.info("Erro ao enviar mensagem para o topico {} {}", topicoKafka, erro));

		return result;
	}

}
