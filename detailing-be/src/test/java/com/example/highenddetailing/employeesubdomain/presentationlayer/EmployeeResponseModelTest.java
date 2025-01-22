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
}
