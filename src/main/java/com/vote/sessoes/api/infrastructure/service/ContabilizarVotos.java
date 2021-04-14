package com.vote.sessoes.api.infrastructure.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Pauta;
import com.vote.sessoes.api.service.sessaoVotacao.PautaService;
import com.vote.sessoes.api.service.sessaoVotacao.VotoService;

@Component
public class ContabilizarVotos {

	Logger log = LoggerFactory.getLogger(ContabilizarVotos.class);

	private PautaService pautaService;
	private VotoService votoService;

	public ContabilizarVotos(PautaService pautaService, VotoService votoService) {
		this.pautaService = pautaService;
		this.votoService = votoService;
	}

	@Scheduled(fixedRate = 5000)
	private void contabilizarVotos() {

		List<Pauta> pautas = pautaService.listarTodasPendenteResultado();

		pautas.forEach(pauta -> {
			votoService.processarResultado(pauta);
		});

	}

}
