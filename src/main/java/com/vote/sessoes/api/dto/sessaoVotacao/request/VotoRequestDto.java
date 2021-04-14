package com.vote.sessoes.api.dto.sessaoVotacao.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.SimNaoEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VotoRequestDto {

	@NotEmpty
	@ApiModelProperty(value = "Identificador da Pauta Disponível para Votação")
	private String pauta;

	@CPF
	@NotEmpty
	@ApiModelProperty(value = "CPF do Associado")
	private String cpf;

	@ApiModelProperty(value = "Voto (SIM / NAO)")
	@NotNull
	private SimNaoEnum opcaoVoto;

}
