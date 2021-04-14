package com.vote.sessoes.api.dto.sessaoVotacao.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociadoRequestDto {

	@CPF
	private String cpf;

	@NotEmpty
	private String nome;

	public AssociadoRequestDto() {
	}

	public AssociadoRequestDto(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}

}
