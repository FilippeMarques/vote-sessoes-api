package com.vote.sessoes.api.exception;

import com.vote.sessoes.api.message.MessageManager;

public class CustomValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomValidationException(String message) {
		super(MessageManager.getMessage(message));
	}
}
