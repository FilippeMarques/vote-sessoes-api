package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SimNaoEnum {
	SIM("Sim"), NAO("Não");

	private final String descricao;

	private SimNaoEnum(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
