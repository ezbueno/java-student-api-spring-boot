package com.buenoezandro.student.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonParseError {

	private int code;
	private String status;
	private OffsetDateTime timestamp;
	private String message;

	@JsonProperty(access = Access.WRITE_ONLY)
	private List<String> errors;

}