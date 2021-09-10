package com.buenoezandro.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buenoezandro.student.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByFirstName(String firstName);

	Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);

	List<Student> findByFirstNameOrLastName(String firstName, String lastName);

	List<Student> findByFirstNameIn(List<String> firstNames);

	List<Student> findByFirstNameContaining(String firstName);

	List<Student> findByFirstNameStartingWith(String firstName);

	List<Student> findByFirstNameEndingWith(String firstName);

	@Query(value = "SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
	List<Student> getByFirstNameAndLastName(@Param(value = "firstName") String firstName,
			@Param(value = "lastName") String lastName);

	@Modifying
	@Query(value = "UPDATE Student s SET s.firstName = :firstName WHERE s.id = :id")
	Integer updateStudent(@Param(value = "id") Long id, @Param(value = "firstName") String firstName);

	@Modifying
	@Query(value = "DELETE FROM Student s WHERE s.firstName = :firstName")
	Optional<Integer> deleteStudent(@Param(value = "firstName") String firstName);

	List<Student> findByAddressCity(String city);

	@Query(value = "SELECT s FROM Student s WHERE s.address.city = :city")
	List<Student> getByAddressCity(@Param(value = "city") String city);

	@Query(nativeQuery = true, value = "SELECT * FROM Student AS s INNER JOIN Address AS a ON s.address_id = a.id WHERE a.city = :city")
	List<Student> getByAddressCityWithNativeQuery(@Param(value = "city") String city);
	 
}