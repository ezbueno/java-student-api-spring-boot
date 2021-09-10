package com.buenoezandro.student.dto;

import java.util.ArrayList;
import java.util.List;

import com.buenoezandro.student.model.Student;
import com.buenoezandro.student.model.Subject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String fullName;
	private String street;
	private String city;
	private List<SubjectDTO> subjects = new ArrayList<>();

	public StudentDTO(Student student) {
		this.id = student.getId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.email = student.getEmail();
		this.fullName = student.getFirstName() + " " + student.getLastName();
		this.street = student.getAddress().getStreet();
		this.city = student.getAddress().getCity();

		if (!student.getSubjects().isEmpty()) {
			for (Subject subject : student.getSubjects()) {
				subjects.add(new SubjectDTO(subject));
			}
		}		
	}

}