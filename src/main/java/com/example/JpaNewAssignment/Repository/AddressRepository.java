package com.example.JpaNewAssignment.Repository;

import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
