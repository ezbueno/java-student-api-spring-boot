package com.buenoezandro.student.service;

import java.util.List;

import com.buenoezandro.student.model.Student;
import com.buenoezandro.student.request.CreateStudentRequest;
import com.buenoezandro.student.request.InQueryRequest;
import com.buenoezandro.student.request.UpdateStudentRequest;

public interface StudentService {

	List<Student> getAllStudents();

	Student createStudent(CreateStudentRequest createStudentRequest);
	
	Student updateStudent(UpdateStudentRequest updateStudentRequest);
	
	void deleteStudent(Long id);
	
	List<Student> getByFirstName(String firstName);
	
	Student getByFirstNameAndLastName(String firstName, String lastName);
	
	List<Student> getByFirstNameOrLastName(String firstName, String lastName);
	
	List<Student> getByFirstNameIn(InQueryRequest inQueryRequest);
	
	List<Student> getAllStudentsWithPagination(int pageNo, int pageSize);
	
	List<Student> getAllStudentsWithSorting();
	
	List<Student> getByFirstNameContains(String firstName);
	
	List<Student> getByFirstNameStartsWith(String firstName);
	
	List<Student> getByFirstNameEndsWith(String firstName);
	
	List<Student> findByFirstNameAndLastNameWithJPQL(String firstName, String lastName);
	
	Integer updateStudentWithJPQL(Long id, String firstName);
	
	Integer deleteStudentWithJPQL(String firstName);
	
	List<Student> getByCity(String city);

}