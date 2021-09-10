package com.buenoezandro.student.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateSubjectRequest {

	@NotBlank
	private String subjectName;
	
	@NotNull
	private Double marksObtained;
	
}