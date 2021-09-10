package com.buenoezandro.student.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.buenoezandro.student.dto.StudentDTO;
import com.buenoezandro.student.model.Student;
import com.buenoezandro.student.request.CreateStudentRequest;
import com.buenoezandro.student.request.InQueryRequest;
import com.buenoezandro.student.request.UpdateStudentRequest;
import com.buenoezandro.student.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/student")
public class StudentController {

	private final StudentService studentService;

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	@GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StudentDTO> getAllStudents() {
		return this.studentService.getAllStudents().stream().map(StudentDTO::new).toList();
	}

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public StudentDTO createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest) {
		LOGGER.info("Saving new user {} to the database", createStudentRequest);
		var student = this.studentService.createStudent(createStudentRequest);
		return new StudentDTO(student);
	}

	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentDTO updateStudent(@Valid @RequestBody UpdateStudentRequest updateStudentRequest) {
		var student = this.studentService.updateStudent(updateStudentRequest);
		return new StudentDTO(student);
	}

	@DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteStudent(@RequestParam(name = "id", defaultValue = "0") Long id) {
		this.studentService.deleteStudent(id);
	}

	@GetMapping(path = "/getByFirstName/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstName(@PathVariable String firstName) {
		List<Student> students = this.studentService.getByFirstName(firstName);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/getByFirstNameAndLastName/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDTO> getByFirstNameAndLastName(@PathVariable String firstName,
			@PathVariable String lastName) {
		var student = this.studentService.getByFirstNameAndLastName(firstName, lastName);

		if (student != null) {
			return ResponseEntity.ok(new StudentDTO(student));
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = "/getByFirstNameOrLastName/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstNameOrLastName(@PathVariable String firstName,
			@PathVariable String lastName) {
		List<Student> students = this.studentService.getByFirstNameOrLastName(firstName, lastName);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/getByFirstNameIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstNameIn(@RequestBody InQueryRequest inQueryRequest) {
		List<Student> students = this.studentService.getByFirstNameIn(inQueryRequest);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/getAllWithPagination", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getAllStudentsWithPagination(
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
		List<Student> students = this.studentService.getAllStudentsWithPagination(pageNo, pageSize);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/getAllWithSorting", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StudentDTO> getAllStudentsWithSorting() {
		return this.studentService.getAllStudentsWithSorting().stream().map(StudentDTO::new).toList();
	}

	@GetMapping(path = "/like/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstNameContains(@PathVariable String firstName) {
		List<Student> students = this.studentService.getByFirstNameContains(firstName);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/startsWith/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstNameStartsWith(@PathVariable String firstName) {
		List<Student> students = this.studentService.getByFirstNameStartsWith(firstName);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/endsWith/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByFirstNameEndsWith(@PathVariable String firstName) {
		List<Student> students = this.studentService.getByFirstNameEndsWith(firstName);
		return this.verifyIfExists(students);
	}

	@GetMapping(path = "/findByFirstNameAndLastName/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> findByFirstNameAndLastNameWithJPQL(@PathVariable String firstName,
			@PathVariable String lastName) {
		List<Student> students = this.studentService.findByFirstNameAndLastNameWithJPQL(firstName, lastName);
		return this.verifyIfExists(students);
	}

	@PutMapping(path = "/updateFirstName/{id}/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateStudentWithJPQL(@PathVariable Long id, @PathVariable String firstName) {
		return this.studentService.updateStudentWithJPQL(id, firstName) + " Student updated!";
	}

	@DeleteMapping(path = "/deleteStudent/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteStudentWithJPQL(@PathVariable String firstName) {
		return this.studentService.deleteStudentWithJPQL(firstName) + " Student(s) deleted!";
	}

	@GetMapping(path = "/getByCity/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDTO>> getByCity(@PathVariable String city) {
		List<Student> students = this.studentService.getByCity(city);
		return this.verifyIfExists(students);
	}

	private ResponseEntity<List<StudentDTO>> verifyIfExists(List<Student> students) {
		if (!students.isEmpty()) {
			return ResponseEntity.ok(students.stream().map(StudentDTO::new).toList());
		}
		return ResponseEntity.notFound().build();
	}

}