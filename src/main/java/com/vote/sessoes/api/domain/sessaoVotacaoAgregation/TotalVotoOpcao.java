package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalVotoOpcao {

	private SimNaoEnum id;
	private Long count;
}
