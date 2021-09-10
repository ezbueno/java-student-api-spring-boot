package com.buenoezandro.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buenoezandro.student.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}