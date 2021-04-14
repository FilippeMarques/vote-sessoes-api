package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ApiIgnore
@Document(value = "voto")
public class Voto {

	@Id
	private String id;

	@Indexed
	@NotNull
	private String pautaId;

	@NotNull
	private String cpf;

	@NotNull
	private SimNaoEnum opcaoVoto;

	private LocalDateTime dataHora;

	public Voto() {
	}

	public Voto(String pautaId, String cpf, SimNaoEnum opcaoVoto, LocalDateTime dataHora) {
		this.pautaId = pautaId;
		this.cpf = cpf;
		this.opcaoVoto = opcaoVoto;
		this.dataHora = dataHora;
	}

}
