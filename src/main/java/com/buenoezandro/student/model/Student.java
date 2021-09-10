package com.buenoezandro.student.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.buenoezandro.student.request.CreateStudentRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	@Transient
	private String fullName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	
	@OneToMany(mappedBy = "student")
	private List<Subject> subjects = new ArrayList<>();
	
	public Student(CreateStudentRequest createStudentRequest) {
		this.firstName = createStudentRequest.getFirstName();
		this.lastName = createStudentRequest.getLastName();
		this.email = createStudentRequest.getEmail();
		this.fullName = createStudentRequest.getFirstName() + " " + createStudentRequest.getLastName();
	}

}