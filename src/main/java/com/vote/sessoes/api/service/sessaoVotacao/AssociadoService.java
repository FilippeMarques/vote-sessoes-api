package com.vote.sessoes.api.service.sessaoVotacao;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Associado;
import com.vote.sessoes.api.dto.sessaoVotacao.request.AssociadoRequestDto;
import com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository.AssociadoRepository;

@Service
public class AssociadoService {

	Logger log = LoggerFactory.getLogger(AssociadoService.class);
	
	private AssociadoRepository associadoRepository;

	public AssociadoService(AssociadoRepository associadoRepository) {
		this.associadoRepository = associadoRepository;
	}

	public Associado save(AssociadoRequestDto associadoDto) {
		log.info("Persistindo associado");
		return associadoRepository.save(new Associado(associadoDto.getCpf(), associadoDto.getNome()));
	}

	public Associado findAvalibleById(String id) {
		log.info("Consultando associado por ID {}", id);
		
		try {
			return associadoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

}
