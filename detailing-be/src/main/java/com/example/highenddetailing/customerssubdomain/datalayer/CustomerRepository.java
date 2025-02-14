package com.example.highenddetailing.customerssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByCustomerIdentifier_CustomerId(String customerId);

    List<Customer> findByCustomerFirstNameAndCustomerLastName(String firstName, String lastName);
    List<Customer> findByCustomerFirstName(String firstName); // ✅ Support multiple results


}
