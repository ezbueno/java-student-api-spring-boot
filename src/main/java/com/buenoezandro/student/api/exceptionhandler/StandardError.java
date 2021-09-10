package com.buenoezandro.student.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
public class StandardError {

	private Integer status;
	private OffsetDateTime dateTime;
	private String message;
	private List<FieldError> fieldErrors;

	@Getter
	@AllArgsConstructor
	public static class FieldError {
		private String fieldName;
		private String message;
	}

}