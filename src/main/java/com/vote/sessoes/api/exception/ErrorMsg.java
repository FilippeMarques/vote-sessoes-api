package com.vote.sessoes.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMsg {
	private String message;

	public ErrorMsg(String message) {
		this.message = message;
	}

}
