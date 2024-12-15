package com.example.highenddetailing.employeesubdomain.datalayer;


import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void whenFindAll_thenReturnAllEmployees() {
        // Arrange: Prepare sample employees
        Employee employee1 = Employee.builder()
                .employeeIdentifier(new EmployeeIdentifier())
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        Employee employee2 = Employee.builder()
                .employeeIdentifier(new EmployeeIdentifier())
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("2323232")
                .salary(55000.00)
                .imagePath("profile.png")
                .build();

        // Save
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Act
        List<Employee> employees = employeeRepository.findAll();

        // Assert
        assertNotNull(employees);
        assertEquals(2, employees.size());

        assertEquals(employee1.getFirst_name(), employees.get(0).getFirst_name());
        assertEquals(employee1.getLast_name(), employees.get(0).getLast_name());
        assertEquals(employee1.getPosition(), employees.get(0).getPosition());
        assertEquals(employee1.getEmail(), employees.get(0).getEmail());
        assertEquals(employee1.getPhone(), employees.get(0).getPhone());
        assertEquals(employee1.getSalary(), employees.get(0).getSalary());
        assertEquals(employee1.getImagePath(), employees.get(0).getImagePath());


        assertEquals(employee2.getFirst_name(), employees.get(1).getFirst_name());
        assertEquals(employee2.getLast_name(), employees.get(1).getLast_name());
        assertEquals(employee2.getPosition(), employees.get(1).getPosition());
        assertEquals(employee2.getEmail(), employees.get(1).getEmail());
        assertEquals(employee2.getPhone(), employees.get(1).getPhone());
        assertEquals(employee2.getSalary(), employees.get(1).getSalary());
        assertEquals(employee2.getImagePath(), employees.get(1).getImagePath());
    }
    @Test
    void whenFindByEmployeeIdentifier_EmployeeId_thenReturnEmployee() {
        // Arrange
        Employee employee = Employee.builder()
                .employeeIdentifier(new EmployeeIdentifier("E001"))
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        employeeRepository.save(employee);

        // Act
        Optional<Employee> foundEmployee = employeeRepository.findByEmployeeIdentifier_EmployeeId("E001");

        // Assert
        assertTrue(foundEmployee.isPresent());
        assertEquals("Jane", foundEmployee.get().getFirst_name());
        assertEquals("Smith", foundEmployee.get().getLast_name());
        assertEquals("Manager", foundEmployee.get().getPosition());
        assertEquals("jane.smith@example.com", foundEmployee.get().getEmail());
        assertEquals("232323", foundEmployee.get().getPhone());
        assertEquals(75000.00, foundEmployee.get().getSalary());
        assertEquals("profile.png", foundEmployee.get().getImagePath());
    }
}
