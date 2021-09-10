package com.buenoezandro.student.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateStudentRequest {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	@Email
	private String email;
		
	@NotBlank
	private String street;
	
	@NotBlank
	private String city;
	
	private List<CreateSubjectRequest> subjectsLearning;
	
}