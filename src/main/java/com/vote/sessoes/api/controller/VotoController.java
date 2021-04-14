package com.vote.sessoes.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote.sessoes.api.dto.sessaoVotacao.ResultadoVotacaoDto;
import com.vote.sessoes.api.dto.sessaoVotacao.request.VotoRequestDto;
import com.vote.sessoes.api.dto.sessaoVotacao.response.IdResponseDto;
import com.vote.sessoes.api.service.sessaoVotacao.VotoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "votar")
public class VotoController {

	private VotoService votoService;

	public VotoController(VotoService votoService) {
		this.votoService = votoService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Votar", produces = "application/json")
	public ResponseEntity<IdResponseDto> votar(@Valid @RequestBody VotoRequestDto votoRequest) {
		return ResponseEntity.ok(new IdResponseDto(
				votoService.votar(votoRequest.getPauta(), votoRequest.getCpf(), votoRequest.getOpcaoVoto()).getId()));
	}

	@GetMapping(value = "/resultado/{pautaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Apuração de Votos na Pauta", produces = "application/json")
	public ResponseEntity<ResultadoVotacaoDto> resultadoVotacao(@PathVariable String pautaId) {
		return ResponseEntity.ok(votoService.calcularResultadoVotacao(pautaId));
	}

}
