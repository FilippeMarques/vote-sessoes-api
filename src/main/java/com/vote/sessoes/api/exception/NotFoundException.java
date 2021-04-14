package com.vote.sessoes.api.exception;

import com.vote.sessoes.api.message.MessageManager;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(MessageManager.getMessage(message));
	}
}
