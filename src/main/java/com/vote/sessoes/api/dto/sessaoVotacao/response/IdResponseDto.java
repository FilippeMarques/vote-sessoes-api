package com.vote.sessoes.api.dto.sessaoVotacao.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdResponseDto {

	private String id;

	public IdResponseDto(String id) {
		this.id = id;
	}
}
