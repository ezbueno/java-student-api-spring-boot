package com.buenoezandro.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buenoezandro.student.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}