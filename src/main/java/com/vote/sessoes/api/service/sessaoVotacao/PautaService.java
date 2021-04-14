package com.vote.sessoes.api.service.sessaoVotacao;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Pauta;
import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Sessao;
import com.vote.sessoes.api.dto.sessaoVotacao.PautaDto;
import com.vote.sessoes.api.exception.CustomValidationException;
import com.vote.sessoes.api.exception.NotFoundException;
import com.vote.sessoes.api.exception.UnprocessableException;
import com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository.PautaRepository;

@Service
public class PautaService {

	Logger log = LoggerFactory.getLogger(PautaService.class);

	private PautaRepository pautaRepository;

	public PautaService(PautaRepository pautaRepository) {
		this.pautaRepository = pautaRepository;
	}

	public Pauta gravar(String nome, String descricao) {
		return save(new Pauta(nome, descricao));
	}

	public Pauta gravar(Pauta pauta) {
		return save(pauta);
	}

	private Pauta save(Pauta pauta) {
		log.info("Persistindo pauta {}", pauta.getNome());

		if (pauta.getNome() == null || pauta.getNome().isBlank()) {
			throw new CustomValidationException("nome.not.blank");
		}

		return pautaRepository.save(pauta);
	}

	public PautaDto buscarPorIdDto(String id) {
		try {
			return pautaRepository.findById(id).get().toDto();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Pauta buscarPorId(String id) {
		try {
			return pautaRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<PautaDto> listarTodasDisponiveis() {
		return pautaRepository.findAllSessaoEmVotacao(LocalDateTime.now(ZoneOffset.UTC)).stream().map(p -> p.toDto())
				.collect(Collectors.toList());
	}

	public List<Pauta> listarTodasPendenteResultado() {
		return pautaRepository.findAllResultadoNaoApurado(LocalDateTime.now(ZoneOffset.UTC));
	}

	public Pauta buscarPorIdDisponivelVotacao(String id) {
		try {
			Pauta pauta = pautaRepository.findById(id).get();

			if (pauta != null && pauta.getSessao() != null && pauta.getSessao().getDataHoraFim() != null
					&& pauta.getSessao().getDataHoraFim().isAfter(LocalDateTime.now(ZoneOffset.UTC))) {
				return pauta;
			}
		} catch (Exception e) {
			log.error("Erro", e);
		}

		return null;
	}

	public void iniciarSessao(String pautaId, Integer duracaoMinutos) {
		if (pautaId == null || pautaId.isBlank()) {
			throw new NotFoundException("pauta.nao.encontrada");
		}

		Pauta pauta = pautaRepository.findById(pautaId).get();
		if (pauta == null) {
			throw new NotFoundException("pauta.nao.encontrada");
		}

		if (pauta.getSessao() != null) {
			if (pauta.getSessao().getDataHoraFim() != null
					&& pauta.getSessao().getDataHoraFim().isBefore(LocalDateTime.now(ZoneOffset.UTC)))
				throw new UnprocessableException("sessao.finalizada");
			else
				throw new UnprocessableException("pauta.em.votacao");
		}

		Sessao sessao = new Sessao();
		sessao.setDataHoraInicio(LocalDateTime.now(ZoneOffset.UTC));
		if (duracaoMinutos == null || duracaoMinutos <= 0) {
			duracaoMinutos = 1; 
		}
		sessao.setMinutosDuracao(duracaoMinutos);
		sessao.setDataHoraFim(sessao.getDataHoraInicio().plusMinutes(duracaoMinutos));

		pauta.setSessao(sessao);
		pautaRepository.save(pauta);
	}

}
