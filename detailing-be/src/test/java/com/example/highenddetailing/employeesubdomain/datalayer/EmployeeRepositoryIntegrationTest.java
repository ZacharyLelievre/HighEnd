package com.example.highenddetailing.employeesubdomain.datalayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
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
        // Arrange: Prepare sample employees using .employeeId(...) instead of .employeeIdentifier(...)
        Employee employee1 = Employee.builder()
                .employeeId("E001")
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        Employee employee2 = Employee.builder()
                .employeeId("E002")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("2323232")
                .salary(55000.00)
                .imagePath("profile.png")
                .build();

        // Save them
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Act
        List<Employee> employees = employeeRepository.findAll();

        // Assert
        assertNotNull(employees);
        assertEquals(2, employees.size());

        // Check the first employee
        Employee found1 = employees.stream()
                .filter(e -> e.getEmployeeId().equals("E001"))
                .findFirst()
                .orElse(null);
        assertNotNull(found1);
        assertEquals("Jane", found1.getFirst_name());
        assertEquals("Smith", found1.getLast_name());
        assertEquals("Manager", found1.getPosition());
        assertEquals("jane.smith@example.com", found1.getEmail());
        assertEquals("232323", found1.getPhone());
        assertEquals(75000.00, found1.getSalary());
        assertEquals("profile.png", found1.getImagePath());

        // Check the second employee
        Employee found2 = employees.stream()
                .filter(e -> e.getEmployeeId().equals("E002"))
                .findFirst()
                .orElse(null);
        assertNotNull(found2);
        assertEquals("John", found2.getFirst_name());
        assertEquals("Doe", found2.getLast_name());
        assertEquals("Technician", found2.getPosition());
        assertEquals("john.doe@example.com", found2.getEmail());
        assertEquals("2323232", found2.getPhone());
        assertEquals(55000.00, found2.getSalary());
        assertEquals("profile.png", found2.getImagePath());
    }

    @Test
    void whenFindByEmployeeId_thenReturnEmployee() {
        // Arrange
        String employeeId = "E123";
        Employee employee = Employee.builder()
                .employeeId(employeeId)
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
        Optional<Employee> foundEmployee = employeeRepository.findByEmployeeId(employeeId);

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

    @Test
    void whenFindByEmployeeId_thenReturnEmployeeWithAvailability() {
        // Arrange
        String employeeId = "e1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Employee employee = Employee.builder()
                .employeeId(employeeId)
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .availability(List.of(
                        new Availability("Monday", "08:00", "12:00"),
                        new Availability("Wednesday", "09:00", "17:00")
                ))
                .build();

        employeeRepository.save(employee);

        // Act
        Optional<Employee> foundEmployee = employeeRepository.findByEmployeeId(employeeId);

        // Assert
        assertTrue(foundEmployee.isPresent());
        Employee actual = foundEmployee.get();
        assertEquals("Jane", actual.getFirst_name());
        assertNotNull(actual.getAvailability());
        assertEquals(2, actual.getAvailability().size());
        assertEquals("Monday", actual.getAvailability().get(0).getDayOfWeek());
        assertEquals("08:00", actual.getAvailability().get(0).getStartTime());
    }
}
