package com.example.highenddetailing.employeesubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeResponseModelTest {
    @Test
    void testEmployeeRequestModelBuilder() {
        // Arrange
        String employeeId = "EMP001";
        String firstName = "John";

        // Act
        EmployeeRequestModel requestModel = EmployeeRequestModel.builder()
                .employeeId(employeeId)
                .first_name(firstName)
                .build();

        // Assert
        assertEquals(requestModel.getEmployeeId(), employeeId);
        assertEquals(requestModel.getFirst_name(), firstName);
    }




    @Test
    void testBuilder() {
        // Act
        EmployeeResponseModel employee = EmployeeResponseModel.builder()
                .employeeId("E001")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890") // Added phone
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg")
                .build();

        // Assert
        assertThat(employee.getEmployeeId()).isEqualTo("E001");
        assertThat(employee.getFirst_name()).isEqualTo("John");
        assertThat(employee.getLast_name()).isEqualTo("Doe");
        assertThat(employee.getPosition()).isEqualTo("Technician");
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(employee.getPhone()).isEqualTo("1234567890"); // Verify phone
        assertThat(employee.getSalary()).isEqualTo(45000.0);
        assertThat(employee.getImagePath()).isEqualTo("/images/employees/johndoe.jpg");
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        EmployeeResponseModel employee = new EmployeeResponseModel();

        // Assert
        assertThat(employee.getEmployeeId()).isNull();
        assertThat(employee.getFirst_name()).isNull();
        assertThat(employee.getLast_name()).isNull();
        assertThat(employee.getPosition()).isNull();
        assertThat(employee.getEmail()).isNull();
        assertThat(employee.getPhone()).isNull(); // Verify phone
        assertThat(employee.getSalary()).isEqualTo(0.0);
        assertThat(employee.getImagePath()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Integer id = 1;
        EmployeeIdentifier employeeIdentifier = new EmployeeIdentifier("E001"); // Correct type
        String firstName = "John";
        String lastName = "Doe";
        String position = "Technician";
        String email = "john.doe@example.com";
        String phone = "1234567890"; // Add phone
        double salary = 45000.0;
        String imagePath = "/images/employees/johndoe.jpg";

        // Act
        Employee employee = new Employee(id, employeeIdentifier, firstName, lastName, position, email, phone, salary, imagePath);

        // Assert
        assertEquals(employee.getId(), id);
        assertEquals(employee.getEmployeeIdentifier(), employeeIdentifier); // Correct assertion
        assertEquals(employee.getFirst_name(), firstName);
        assertEquals(employee.getLast_name(), lastName);
        assertEquals(employee.getPosition(), position);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPhone(), phone); // Verify phone
        assertEquals(employee.getSalary(), salary);
        assertEquals(employee.getImagePath(), imagePath);
    }

    @Test
    void testSetters() {
        // Arrange
        EmployeeResponseModel employee = new EmployeeResponseModel();

        // Act
        employee.setEmployeeId("E002");
        employee.setFirst_name("Jane");
        employee.setLast_name("Smith");
        employee.setPosition("Manager");
        employee.setEmail("jane.smith@example.com");
        employee.setPhone("0987654321"); // Set phone
        employee.setSalary(55000.0);
        employee.setImagePath("/images/employees/janesmith.jpg");

        // Assert
        assertThat(employee.getEmployeeId()).isEqualTo("E002");
        assertThat(employee.getFirst_name()).isEqualTo("Jane");
        assertThat(employee.getLast_name()).isEqualTo("Smith");
        assertThat(employee.getPosition()).isEqualTo("Manager");
        assertThat(employee.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(employee.getPhone()).isEqualTo("0987654321"); // Verify phone
        assertThat(employee.getSalary()).isEqualTo(55000.0);
        assertThat(employee.getImagePath()).isEqualTo("/images/employees/janesmith.jpg");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmployeeResponseModel employee1 = new EmployeeResponseModel(
                "E001",
                "John",
                "Doe",
                "Technician",
                "john.doe@example.com",
                "1234567890", // Add phone
                45000.0,
                "/images/employees/johndoe.jpg"
        );

        EmployeeResponseModel employee2 = new EmployeeResponseModel(
                "E001",
                "John",
                "Doe",
                "Technician",
                "john.doe@example.com",
                "1234567890", // Add phone
                45000.0,
                "/images/employees/johndoe.jpg"
        );

        EmployeeResponseModel employee3 = new EmployeeResponseModel(
                "E002",
                "Jane",
                "Smith",
                "Manager",
                "jane.smith@example.com",
                "0987654321", // Add phone
                50000.0,
                "/images/employees/janesmith.jpg"
        );

        // Act & Assert
        assertThat(employee1).isEqualTo(employee2); // equals()
        assertThat(employee1.hashCode()).isEqualTo(employee2.hashCode()); // hashCode()
        assertThat(employee1).isNotEqualTo(employee3); // Negative case for equals()
        assertThat(employee1.hashCode()).isNotEqualTo(employee3.hashCode()); // Negative case for hashCode()
    }

    @Test
    void testToString() {
        // Arrange
        Employee.EmployeeBuilder builder = Employee.builder();
        builder.id(1)
                .employeeIdentifier(new EmployeeIdentifier("E001"))
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890") // Add phone
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "first_name=John",
                "last_name=Doe",
                "position=Technician",
                "email=john.doe@example.com",
                "phone=1234567890" // Verify phone
        );
    }

    @Test
    void testBuild() {
        // Arrange
        Employee.EmployeeBuilder builder = Employee.builder();
        builder.id(1)
                .employeeIdentifier(new EmployeeIdentifier("E001"))
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890") // Add phone
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg");

        // Act
        Employee employee = builder.build();

        // Assert
        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getEmployeeIdentifier()).isEqualTo(new EmployeeIdentifier("E001"));
        assertThat(employee.getFirst_name()).isEqualTo("John");
        assertThat(employee.getLast_name()).isEqualTo("Doe");
        assertThat(employee.getPosition()).isEqualTo("Technician");
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(employee.getPhone()).isEqualTo("1234567890"); // Verify phone
        assertThat(employee.getSalary()).isEqualTo(45000.0);
        assertThat(employee.getImagePath()).isEqualTo("/images/employees/johndoe.jpg");
    }
}
