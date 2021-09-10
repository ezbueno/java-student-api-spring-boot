package com.buenoezandro.student.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.buenoezandro.student.exception.StudentNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	private static final String ERROR_MESSAGE = "One or more fields have an error. Please make sure all fields are filled in correctly!";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<StandardError.FieldError> errors = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

			errors.add(new StandardError.FieldError(fieldName, message));
		});

		var error = new StandardError();
		error.setStatus(status.value());
		error.setDateTime(OffsetDateTime.now());
		error.setMessage(ERROR_MESSAGE);
		error.setFieldErrors(errors);

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		var error = new StandardError();
		error.setStatus(status.value());
		error.setDateTime(OffsetDateTime.now());
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(PropertyReferenceException.class)
	private ResponseEntity<Object> handlePropertyReference(PropertyReferenceException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		var error = new StandardError();
		error.setStatus(status.value());
		error.setDateTime(OffsetDateTime.now());
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return this.buildResponseEntity(HttpStatus.BAD_REQUEST, "Malformed JSON body and/or field error",
				Collections.singletonList(exception.getLocalizedMessage()));
	}

	@ExceptionHandler(StudentNotFoundException.class)
	private ResponseEntity<Object> handleStudentNotFound(StudentNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		var error = new StandardError();
		error.setStatus(status.value());
		error.setDateTime(OffsetDateTime.now());
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
		var error = JsonParseError.builder().code(httpStatus.value()).status(httpStatus.getReasonPhrase())
				.message(message).errors(errors).timestamp(OffsetDateTime.now()).build();
		return ResponseEntity.status(httpStatus).body(error);
	}

}