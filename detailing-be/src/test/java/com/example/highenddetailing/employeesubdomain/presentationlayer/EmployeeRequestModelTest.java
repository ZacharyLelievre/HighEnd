package com.example.highenddetailing.employeesubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeRequestModelTest {

    @Test
    void testEmployeeRequestModelBuilder() {
        // Arrange
        String employeeId = "emp123";
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
    void testNoArgsConstructor() {
        // Act
        EmployeeRequestModel requestModel = new EmployeeRequestModel();

        // Assert
        assertThat(requestModel.getEmployeeId()).isNull();
        assertThat(requestModel.getFirst_name()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String employeeId = "emp123";
        String firstName = "John";
        String lastName = "Doe";
        String position = "Technician";
        String email = "john.doe@example.com";
        String phone = "123-456-7890";
        double salary = 60000.0;
        String imagePath = "/images/john-doe.jpg";

        // Act
        EmployeeRequestModel requestModel = new EmployeeRequestModel(
                employeeId,
                firstName,
                lastName,
                position,
                email,
                phone,
                salary,
                imagePath
        );

        // Assert
        assertEquals(employeeId, requestModel.getEmployeeId());
        assertEquals(firstName, requestModel.getFirst_name());
        assertEquals(lastName, requestModel.getLast_name());
        assertEquals(position, requestModel.getPosition());
        assertEquals(email, requestModel.getEmail());
        assertEquals(phone, requestModel.getPhone());
        assertEquals(salary, requestModel.getSalary(), 0.001);
        assertEquals(imagePath, requestModel.getImagePath());
    }

    @Test
    void testSetters() {
        // Arrange
        EmployeeRequestModel requestModel = new EmployeeRequestModel();

        // Act
        requestModel.setEmployeeId("emp123");
        requestModel.setFirst_name("John");

        // Assert
        assertThat(requestModel.getEmployeeId()).isEqualTo("emp123");
        assertThat(requestModel.getFirst_name()).isEqualTo("John");
    }

    @Test
    void testGetters() {
        // Arrange
        EmployeeRequestModel requestModel = new EmployeeRequestModel();
        requestModel.setEmployeeId("emp123");
        requestModel.setFirst_name("John");

        // Act & Assert
        assertEquals("emp123", requestModel.getEmployeeId());
        assertEquals("John", requestModel.getFirst_name());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmployeeRequestModel requestModel1 = new EmployeeRequestModel(
                "emp123",    // employeeId
                "John",      // first_name
                "Doe",       // last_name
                "Technician",// position
                "john.doe@example.com", // email
                "123-456-7890",         // phone
                60000.0,                // salary
                "/images/john-doe.jpg"  // imagePath
        );

        EmployeeRequestModel requestModel2 = new EmployeeRequestModel(
                "emp123",    // employeeId
                "John",      // first_name
                "Doe",       // last_name
                "Technician",// position
                "john.doe@example.com", // email
                "123-456-7890",         // phone
                60000.0,                // salary
                "/images/john-doe.jpg"  // imagePath
        );

        EmployeeRequestModel requestModel3 = new EmployeeRequestModel(
                "emp124",    // employeeId
                "Jane",      // first_name
                "Smith",     // last_name
                "Manager",   // position
                "jane.smith@example.com", // email
                "987-654-3210",           // phone
                80000.0,                  // salary
                "/images/jane-smith.jpg"  // imagePath
        );

        // Act & Assert
        assertThat(requestModel1).isEqualTo(requestModel2);
        assertThat(requestModel1.hashCode()).isEqualTo(requestModel2.hashCode());

        assertThat(requestModel1).isNotEqualTo(requestModel3);
        assertThat(requestModel1.hashCode()).isNotEqualTo(requestModel3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        EmployeeRequestModel requestModel = EmployeeRequestModel.builder()
                .employeeId("emp123")
                .first_name("John")
                .build();

        // Act
        String toStringResult = requestModel.toString();

        // Assert
        assertThat(toStringResult).contains(
                "employeeId=emp123",
                "first_name=John"
        );
    }
}