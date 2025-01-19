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

        // Act
        EmployeeRequestModel requestModel = new EmployeeRequestModel(employeeId, firstName);

        // Assert
        assertEquals(requestModel.getEmployeeId(), employeeId);
        assertEquals(requestModel.getFirst_name(), firstName);
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
        EmployeeRequestModel requestModel1 = new EmployeeRequestModel("emp123", "John");
        EmployeeRequestModel requestModel2 = new EmployeeRequestModel("emp123", "John");
        EmployeeRequestModel requestModel3 = new EmployeeRequestModel("emp124", "Jane");

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