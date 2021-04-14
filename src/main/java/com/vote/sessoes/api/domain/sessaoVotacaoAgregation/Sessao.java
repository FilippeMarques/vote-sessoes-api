package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ApiIgnore
public class Sessao {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss", timezone = "America/Sao_Paulo")
	private LocalDateTime dataHoraInicio;

	private int minutosDuracao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss", timezone = "America/Sao_Paulo")
	private LocalDateTime dataHoraFim;

	public Sessao() {
	}

}
