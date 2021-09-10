package com.buenoezandro.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buenoezandro.student.exception.StudentNotFoundException;
import com.buenoezandro.student.model.Address;
import com.buenoezandro.student.model.Student;
import com.buenoezandro.student.model.Subject;
import com.buenoezandro.student.repository.AddressRepository;
import com.buenoezandro.student.repository.StudentRepository;
import com.buenoezandro.student.repository.SubjectRepository;
import com.buenoezandro.student.request.CreateStudentRequest;
import com.buenoezandro.student.request.CreateSubjectRequest;
import com.buenoezandro.student.request.InQueryRequest;
import com.buenoezandro.student.request.UpdateStudentRequest;
import com.buenoezandro.student.request.UpdateSubjectRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final AddressRepository addressRepository;
	private final SubjectRepository subjectRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Student> getAllStudents() {
		return this.studentRepository.findAll();
	}

	@Transactional
	@Override
	public Student createStudent(CreateStudentRequest createStudentRequest) {
		var student = new Student(createStudentRequest);

		var address = new Address();
		address.setStreet(createStudentRequest.getStreet());
		address.setCity(createStudentRequest.getCity());
		address = this.addressRepository.save(address);

		student.setAddress(address);

		if (!createStudentRequest.getSubjectsLearning().isEmpty()) {
			for (CreateSubjectRequest createSubjectRequest : createStudentRequest.getSubjectsLearning()) {
				var subject = new Subject();
				subject.setName(createSubjectRequest.getSubjectName());
				subject.setMarksObtained(createSubjectRequest.getMarksObtained());
				subject.setStudent(student);

				subject = this.subjectRepository.save(subject);

				student.getSubjects().add(subject);
			}
		}
		return this.studentRepository.save(student);
	}

	@Transactional
	@Override
	public Student updateStudent(UpdateStudentRequest updateStudentRequest) {
		var student = this.getStudentByIdOrElseThrow(updateStudentRequest.getId());

		student.setFirstName(updateStudentRequest.getFirstName());
		student.setLastName(updateStudentRequest.getLastName());
		student.setEmail(updateStudentRequest.getEmail());

		var address = this.addressRepository.findById(student.getAddress().getId()).orElse(new Address());

		address.setStreet(updateStudentRequest.getStreet());
		address.setCity(updateStudentRequest.getCity());

		student.setAddress(address);

		if (!updateStudentRequest.getSubjectsLearning().isEmpty()) {
			for (UpdateSubjectRequest updateSubjectRequest : updateStudentRequest.getSubjectsLearning()) {
				var subject = this.subjectRepository.findById(updateSubjectRequest.getId()).orElse(new Subject());

				subject.setName(updateSubjectRequest.getSubjectName());
				subject.setMarksObtained(updateSubjectRequest.getMarksObtained());
				subject.setStudent(student);

				subject = this.subjectRepository.save(subject);

				student.getSubjects().add(subject);
			}
		}
		return this.studentRepository.save(student);
	}

	@Transactional
	@Override
	public void deleteStudent(Long id) {
		this.getStudentByIdOrElseThrow(id);
		this.studentRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstName(String firstName) {
		return this.studentRepository.findByFirstName(firstName);
	}

	@Transactional(readOnly = true)
	@Override
	public Student getByFirstNameAndLastName(String firstName, String lastName) {
		return this.studentRepository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new StudentNotFoundException("There are no records!"));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstNameOrLastName(String firstName, String lastName) {
		return this.studentRepository.findByFirstNameOrLastName(firstName, lastName);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstNameIn(InQueryRequest inQueryRequest) {
		return this.studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames());
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getAllStudentsWithPagination(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.studentRepository.findAll(pageable).getContent();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getAllStudentsWithSorting() {
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
		return this.studentRepository.findAll(sort);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstNameContains(String firstName) {
		return this.studentRepository.findByFirstNameContaining(firstName);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstNameStartsWith(String firstName) {
		return this.studentRepository.findByFirstNameStartingWith(firstName);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByFirstNameEndsWith(String firstName) {
		return this.studentRepository.findByFirstNameEndingWith(firstName);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> findByFirstNameAndLastNameWithJPQL(String firstName, String lastName) {
		return this.studentRepository.getByFirstNameAndLastName(firstName, lastName);
	}

	@Transactional
	@Override
	public Integer updateStudentWithJPQL(Long id, String firstName) {
		this.getStudentByIdOrElseThrow(id);
		return this.studentRepository.updateStudent(id, firstName);
	}

	@Transactional
	@Override
	public Integer deleteStudentWithJPQL(String firstName) {
		Optional<Integer> id = this.studentRepository.deleteStudent(firstName);

		if (id.isPresent() && id.get().equals(0)) {
			throw new StudentNotFoundException("Student with first name '" + firstName + "' not found!");
		}

		return Integer.valueOf(1);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getByCity(String city) {
		return this.studentRepository.getByAddressCity(city);
	}

	private Student getStudentByIdOrElseThrow(Long id) {
		return this.studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student with ID: " + id + " not found!"));
	}

}