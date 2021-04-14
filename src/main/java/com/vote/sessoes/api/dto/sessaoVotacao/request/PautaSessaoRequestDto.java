package com.vote.sessoes.api.dto.sessaoVotacao.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PautaSessaoRequestDto {

	@ApiModelProperty(value = "Identificador da Pauta")
	@NotEmpty
	private String pauta;

	@ApiModelProperty(value = "Tempo de Duração da Pauta em Minutos")
	private Integer duracao;

}
