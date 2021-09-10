package com.buenoezandro.student.dto;

import com.buenoezandro.student.model.Subject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubjectDTO {

	private Long id;
	private String subjectName;
	private Double marksObtained;

	public SubjectDTO(Subject subject) {
		this.id = subject.getId();
		this.subjectName = subject.getName();
		this.marksObtained = subject.getMarksObtained();
	}

}