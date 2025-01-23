package com.example.highenddetailing.employeesubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
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
        assertEquals(employeeId, requestModel.getEmployeeId());
        assertEquals(firstName, requestModel.getFirst_name());
    }

    @Test
    void testBuilderForResponseModel() {
        // Arrange & Act
        EmployeeResponseModel employee = EmployeeResponseModel.builder()
                .employeeId("E001")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg")
                .build();

        // Assert
        assertThat(employee.getEmployeeId()).isEqualTo("E001");
        assertThat(employee.getFirst_name()).isEqualTo("John");
        assertThat(employee.getLast_name()).isEqualTo("Doe");
        assertThat(employee.getPosition()).isEqualTo("Technician");
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(employee.getPhone()).isEqualTo("1234567890");
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
        assertThat(employee.getPhone()).isNull();
        assertThat(employee.getSalary()).isEqualTo(0.0);
        assertThat(employee.getImagePath()).isNull();
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
        employee.setPhone("0987654321");
        employee.setSalary(55000.0);
        employee.setImagePath("/images/employees/janesmith.jpg");

        // Assert
        assertThat(employee.getEmployeeId()).isEqualTo("E002");
        assertThat(employee.getFirst_name()).isEqualTo("Jane");
        assertThat(employee.getLast_name()).isEqualTo("Smith");
        assertThat(employee.getPosition()).isEqualTo("Manager");
        assertThat(employee.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(employee.getPhone()).isEqualTo("0987654321");
        assertThat(employee.getSalary()).isEqualTo(55000.0);
        assertThat(employee.getImagePath()).isEqualTo("/images/employees/janesmith.jpg");
    }

    @Test
    void testToString() {
        // Arrange
        EmployeeResponseModel employee = EmployeeResponseModel.builder()
                .employeeId("E001")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg")
                .build();

        // Act
        String toStringResult = employee.toString();

        // Assert
        // Depending on Lombok's default toString, these substrings should appear:
        assertThat(toStringResult).contains("E001", "John", "Doe", "Technician", "john.doe@example.com", "1234567890");
    }
    @Test
    void testEquals() {
        // Arrange
        EmployeeResponseModel employee1 = EmployeeResponseModel.builder()
                .employeeId("E001")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg")
                .build();

        EmployeeResponseModel employee2 = EmployeeResponseModel.builder()
                .employeeId("E001")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(45000.0)
                .imagePath("/images/employees/johndoe.jpg")
                .build();

        EmployeeResponseModel employee3 = EmployeeResponseModel.builder()
                .employeeId("E002")
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .salary(55000.0)
                .imagePath("/images/employees/janesmith.jpg")
                .build();

        // Act & Assert
        // Test equality between two identical objects
        assertThat(employee1.equals(employee2)).isTrue();
        assertThat(employee1.hashCode()).isEqualTo(employee2.hashCode());

        // Test inequality with a different object
        assertThat(employee1.equals(employee3)).isFalse();
        assertThat(employee1.hashCode()).isNotEqualTo(employee3.hashCode());

        // Test inequality with null
        assertThat(employee1.equals(null)).isFalse();

        // Test inequality with an object of a different class
        assertThat(employee1.equals(new Object())).isFalse();
    }
    @Test
    void testEqualsAvailability() {
        // Arrange
        AvailabilityResponseModel availability1 = new AvailabilityResponseModel(
                "Monday",
                "09:00",
                "17:00"
        );

        AvailabilityResponseModel availability2 = new AvailabilityResponseModel(
                "Monday",
                "09:00",
                "17:00"
        );

        AvailabilityResponseModel availability3 = new AvailabilityResponseModel(
                "Tuesday",
                "10:00",
                "18:00"
        );

        // Act & Assert
        // Test equality between two identical objects
        assertThat(availability1.equals(availability2)).isTrue();
        assertThat(availability1.hashCode()).isEqualTo(availability2.hashCode());

        // Test inequality with a different object
        assertThat(availability1.equals(availability3)).isFalse();
        assertThat(availability1.hashCode()).isNotEqualTo(availability3.hashCode());

        // Test inequality with null
        assertThat(availability1.equals(null)).isFalse();

        // Test inequality with an object of a different class
        assertThat(availability1.equals(new Object())).isFalse();
    }
}
