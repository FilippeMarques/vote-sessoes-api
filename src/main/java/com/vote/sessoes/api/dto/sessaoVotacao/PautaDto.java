package com.vote.sessoes.api.dto.sessaoVotacao;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Sessao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PautaDto {

	private String id;

	private String nome;

	private String descricao;

	private Sessao sessao;

	public PautaDto(String id, String nome, String descricao, Sessao sessao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.sessao = sessao;
	}
}
