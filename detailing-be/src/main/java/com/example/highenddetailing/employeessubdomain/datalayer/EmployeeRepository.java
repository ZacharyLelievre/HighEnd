package com.example.highenddetailing.employeessubdomain.datalayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    //Optional<Employee> findByEmployeeId(String employeeId);

    Optional<Employee> findByEmployeeIdentifier_EmployeeId(String employeeId);
}