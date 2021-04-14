package com.vote.sessoes.api.exception;

import com.vote.sessoes.api.message.MessageManager;

public class UnprocessableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnprocessableException(String message) {
		super(MessageManager.getMessage(message));
	}
}
