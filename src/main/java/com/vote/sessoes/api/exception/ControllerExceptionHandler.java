package com.vote.sessoes.api.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.vote.sessoes.api.message.Messages;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@Bean
	public ErrorAttributes errorAttributes() {

		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
				errorAttributes.remove("timestamp");
				errorAttributes.remove("exception");
				errorAttributes.remove("trace");
				errorAttributes.remove("error");
				return errorAttributes;
			}
		};
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorMsg> validacaoDeCamposHandler(MethodArgumentNotValidException ex) {
		List<ErrorMsg> ret = new ArrayList<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			ret.add(new ErrorMsg(
					String.format("O campo '%s' %s", fieldError.getField(), fieldError.getDefaultMessage())));
		}

		return ret;
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public void handleConverterException(HttpServletResponse res, HttpMessageConversionException ex)
			throws IOException {
		res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(UnprocessableException.class)
	public void handleUnprocessableException(HttpServletResponse res, UnprocessableException ex) throws IOException {
		res.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	public void handleNotFoundException(HttpServletResponse res, NotFoundException ex) throws IOException {
		res.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(CustomValidationException.class)
	public void handleNotFoundException(HttpServletResponse res, CustomValidationException ex) throws IOException {
		res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.ERRO_INTERNO);
	}

}
