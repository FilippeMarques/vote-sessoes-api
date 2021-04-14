package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ApiIgnore
@Document(value = "associado")
public class Associado {

	@Id
	@CPF
	private String cpf;

	@NotEmpty
	private String nome;

	public Associado() {
	}

	public Associado(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}

}