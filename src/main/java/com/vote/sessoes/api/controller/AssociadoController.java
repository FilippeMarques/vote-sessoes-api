package com.vote.sessoes.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote.sessoes.api.dto.sessaoVotacao.request.AssociadoRequestDto;
import com.vote.sessoes.api.dto.sessaoVotacao.response.IdResponseDto;
import com.vote.sessoes.api.service.sessaoVotacao.AssociadoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "associados")
public class AssociadoController {

	private AssociadoService associadoService;

	public AssociadoController(AssociadoService associadoService) {
		this.associadoService = associadoService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cadastrar associado", produces = "application/json")
	public ResponseEntity<IdResponseDto> cadastrar(@Valid @RequestBody AssociadoRequestDto associado) {
		return ResponseEntity.ok(new IdResponseDto(associadoService.save(associado).getCpf()));
	}

}
