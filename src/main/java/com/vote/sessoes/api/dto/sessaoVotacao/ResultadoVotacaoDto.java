package com.vote.sessoes.api.dto.sessaoVotacao;

import java.util.List;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.TotalVotoOpcao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultadoVotacaoDto {

	private String pauta;
	private List<TotalVotoOpcao> totalVotos;

	public ResultadoVotacaoDto() {
	}

	public ResultadoVotacaoDto(String pauta, List<TotalVotoOpcao> totalVotos) {
		this.pauta = pauta;
		this.totalVotos = totalVotos;
	}

}
