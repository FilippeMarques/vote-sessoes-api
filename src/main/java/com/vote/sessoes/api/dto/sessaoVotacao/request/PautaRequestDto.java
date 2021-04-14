package com.vote.sessoes.api.dto.sessaoVotacao.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PautaRequestDto {

	@ApiModelProperty(required = true)
	@NotEmpty
	private String nome;

	private String descricao;

	public PautaRequestDto() {
	}

	public PautaRequestDto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "PautaRequest [nome=" + nome + ", descricao=" + descricao + "]";
	}

}
