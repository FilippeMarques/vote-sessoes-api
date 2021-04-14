package com.vote.sessoes.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote.sessoes.api.dto.sessaoVotacao.PautaDto;
import com.vote.sessoes.api.dto.sessaoVotacao.request.PautaRequestDto;
import com.vote.sessoes.api.dto.sessaoVotacao.request.PautaSessaoRequestDto;
import com.vote.sessoes.api.dto.sessaoVotacao.response.IdResponseDto;
import com.vote.sessoes.api.message.Messages;
import com.vote.sessoes.api.service.sessaoVotacao.PautaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/pautas")
public class PautaController {

	Logger log = LoggerFactory.getLogger(PautaController.class);

	private PautaService pautaService;

	public PautaController(PautaService pautaService) {
		this.pautaService = pautaService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criar nova pauta", produces = "application/json")
	@ApiResponses({ //
			@ApiResponse(code = 400, message = "nome.not.blank"), //
			@ApiResponse(code = 500, message = Messages.ERRO_INTERNO) //
	})
	public ResponseEntity<IdResponseDto> cadastrar(@Valid @RequestBody PautaRequestDto pauta) {
		log.info("Cadastrando Pauta {}", pauta);
		return ResponseEntity.ok(new IdResponseDto(pautaService.gravar(pauta.getNome(), pauta.getDescricao()).getId()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Listar Pautas Disponíveis para Votacao", produces = "application/json")
	public List<PautaDto> pautasEmVotacao() {
		log.info("Consultando Pautas em Votacao");
		List<PautaDto> pautas = pautaService.listarTodasDisponiveis();
		return pautas;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Pauta por ID", produces = "application/json")
	public PautaDto pauta(@PathVariable String id) {
		log.info("Consultando Pauta: Id {} ", id);
		return pautaService.buscarPorIdDto(id);
	}

	@PostMapping(value = "/iniciarsessao", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Iniciar sessão para votação em uma pauta")
	public void iniciarSessao(@Valid @RequestBody PautaSessaoRequestDto request) {
		log.info("Iniciando sessao: {}", request);
		pautaService.iniciarSessao(request.getPauta(), request.getDuracao());
	}

}
