package com.buenoezandro.student.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubjectRequest {
	
	@NotNull
	private Long id;

	@NotBlank
	private String subjectName;
	
	@NotNull
	private Double marksObtained;
	
}