package com.example.highenddetailing.appointmentsubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.utlis.ErrorResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentTest {

    @Test
    void testEqualsAndHashCode() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setCustomerId("CUST123");
        appointment1.setCustomerName("John Doe");
        appointment1.setServiceId("SERVICE001");
        appointment1.setServiceName("Car Wash");
        appointment1.setEmployeeId("EMP001");
        appointment1.setEmployeeName("Jane Smith");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus(Status.CONFIRMED);
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setCustomerId("CUST123");
        appointment2.setCustomerName("John Doe");
        appointment2.setServiceId("SERVICE001");
        appointment2.setServiceName("Car Wash");
        appointment2.setEmployeeId("EMP001");
        appointment2.setEmployeeName("Jane Smith");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus(Status.CONFIRMED);
        appointment2.setImagePath("/images/appointment1.jpg");

        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setCustomerId("CUST123");
        appointment.setCustomerName("John Doe");
        appointment.setServiceId("SERVICE001");
        appointment.setServiceName("Car Wash");
        appointment.setEmployeeId("EMP001");
        appointment.setEmployeeName("Jane Smith");
        appointment.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus(Status.CONFIRMED);
        appointment.setImagePath("/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("CUST123", appointment.getCustomerId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals("Jane Smith", appointment.getEmployeeName());
    }

    @Test
    void testCanEqual() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setCustomerName("John Doe");
        appointment1.setServiceName("Car Wash");
        appointment1.setEmployeeName("Jane Smith");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setCustomerName("John Doe");
        appointment2.setServiceName("Car Wash");
        appointment2.setEmployeeName("Jane Smith");

        assertThat(appointment1.equals(appointment2)).isTrue();
        assertThat(appointment1.equals(new Object())).isFalse();
    }

    @Test
    void testConstructorWithAllArgs() {
        Appointment appointment = new Appointment(1, "CUST123", "John Doe", "EMP001", "Jane Smith", "SERVICE001", "Car Wash", "2024-12-25", "10:30", "11:30", Status.CONFIRMED, "/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("CUST123", appointment.getCustomerId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("EMP001", appointment.getEmployeeId());
        assertEquals("Jane Smith", appointment.getEmployeeName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals(LocalDate.of(2024, 12, 25), appointment.getAppointmentDate());
        assertEquals(LocalTime.of(10, 30), appointment.getAppointmentTime());
        assertEquals(Status.CONFIRMED, appointment.getStatus());
        assertEquals("/images/appointment1.jpg", appointment.getImagePath());
    }

    @Test
    void testAppointmentBuilder() {
        Appointment appointment = Appointment.builder()
                .id(1)
                .customerId("CUST123")
                .customerName("John Doe")
                .serviceId("SERVICE001")
                .serviceName("Car Wash")
                .employeeId("EMP001")
                .employeeName("Jane Smith")
                .appointmentDate(LocalDate.of(2024, 12, 25))
                .appointmentTime(LocalTime.of(10, 30))
                .status(Status.CONFIRMED)
                .imagePath("/images/appointment1.jpg")
                .build();

        assertEquals(1, appointment.getId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals("Jane Smith", appointment.getEmployeeName());
    }

    @Test
    void testEquals() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setCustomerName("John Doe");
        appointment1.setServiceName("Car Wash");
        appointment1.setEmployeeName("Jane Smith");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setCustomerName("John Doe");
        appointment2.setServiceName("Car Wash");
        appointment2.setEmployeeName("Jane Smith");

        assertThat(appointment1).isEqualTo(appointment2);
    }

    @Test
    void testHashCode() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setCustomerName("John Doe");
        appointment1.setServiceName("Car Wash");
        appointment1.setEmployeeName("Jane Smith");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setCustomerName("John Doe");
        appointment2.setServiceName("Car Wash");
        appointment2.setEmployeeName("Jane Smith");

        assertThat(appointment1.hashCode()).isEqualTo(appointment2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setCustomerId("CUST123");
        appointment.setCustomerName("John Doe");
        appointment.setServiceId("SERVICE001");
        appointment.setServiceName("Car Wash");
        appointment.setEmployeeId("EMP001");
        appointment.setEmployeeName("Jane Smith");
        appointment.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus(Status.CONFIRMED);
        appointment.setImagePath("/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals("Jane Smith", appointment.getEmployeeName());
    }
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        String message = "Error occurred";
        String timestamp = LocalDateTime.now().toString();
        int status = 404;
        String path = "/api/resource";

        ErrorResponse errorResponse1 = new ErrorResponse(message, timestamp, status, path);
        ErrorResponse errorResponse2 = new ErrorResponse(message, timestamp, status, path);

        // Act & Assert
        assertEquals(errorResponse1, errorResponse2, "Both ErrorResponse objects should be equal");
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void testSettersAndGetters2() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse("Initial Message", "2024-01-01T10:00:00", 500, "/initial/path");

        // Act
        errorResponse.setMessage("Updated Message");
        errorResponse.setTimestamp("2024-01-01T11:00:00");
        errorResponse.setStatus(400);
        errorResponse.setPath("/updated/path");

        // Assert
        assertEquals("Updated Message", errorResponse.getMessage(), "Message should be updated correctly");
        assertEquals("2024-01-01T11:00:00", errorResponse.getTimestamp(), "Timestamp should be updated correctly");
        assertEquals(400, errorResponse.getStatus(), "Status should be updated correctly");
        assertEquals("/updated/path", errorResponse.getPath(), "Path should be updated correctly");
    }

    @Test
    void testCanEqual2() {
        // Arrange
        ErrorResponse errorResponse1 = new ErrorResponse("Message1", "2024-01-01T10:00:00", 500, "/path1");
        ErrorResponse errorResponse2 = new ErrorResponse("Message1", "2024-01-01T10:00:00", 500, "/path1");
        Object otherObject = new Object();

        // Act & Assert
        assertThat(errorResponse1.equals(errorResponse2)).isTrue();
        assertThat(errorResponse1.equals(otherObject)).isFalse();
    }

    @Test
    void testConstructorWithAllArgs2() {
        // Arrange
        String message = "Error occurred";
        String timestamp = "2024-01-01T10:00:00";
        int status = 404;
        String path = "/api/resource";

        // Act
        ErrorResponse errorResponse = new ErrorResponse(message, timestamp, status, path);

        // Assert
        assertEquals(message, errorResponse.getMessage(), "Message should be initialized correctly");
        assertEquals(timestamp, errorResponse.getTimestamp(), "Timestamp should be initialized correctly");
        assertEquals(status, errorResponse.getStatus(), "Status should be initialized correctly");
        assertEquals(path, errorResponse.getPath(), "Path should be initialized correctly");
    }



    @Test
    void testEquals2() {
        // Arrange
        ErrorResponse errorResponse1 = new ErrorResponse("Error occurred", "2024-01-01T10:00:00", 404, "/api/resource");
        ErrorResponse errorResponse2 = new ErrorResponse("Error occurred", "2024-01-01T10:00:00", 404, "/api/resource");
        ErrorResponse errorResponse3 = new ErrorResponse("Different error", "2024-01-01T11:00:00", 500, "/api/other");

        // Act & Assert
        assertThat(errorResponse1).isEqualTo(errorResponse2);
        assertThat(errorResponse1).isNotEqualTo(errorResponse3);
    }

    @Test
    void testHashCode2() {
        // Arrange
        ErrorResponse errorResponse1 = new ErrorResponse("Error occurred", "2024-01-01T10:00:00", 404, "/api/resource");
        ErrorResponse errorResponse2 = new ErrorResponse("Error occurred", "2024-01-01T10:00:00", 404, "/api/resource");
        ErrorResponse errorResponse3 = new ErrorResponse("Different error", "2024-01-01T11:00:00", 500, "/api/other");

        // Act & Assert
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode(), "Hash codes should be equal for identical objects");
        assertNotEquals(errorResponse1.hashCode(), errorResponse3.hashCode(), "Hash codes should differ for different objects");
    }

    @Test
    void testGettersAndSetters2() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse("Initial Message", "2024-01-01T10:00:00", 500, "/initial/path");

        // Act
        errorResponse.setMessage("New Message");
        errorResponse.setTimestamp("2024-01-01T12:00:00");
        errorResponse.setStatus(401);
        errorResponse.setPath("/new/path");

        // Assert
        assertEquals("New Message", errorResponse.getMessage(), "Getter for message should return updated value");
        assertEquals("2024-01-01T12:00:00", errorResponse.getTimestamp(), "Getter for timestamp should return updated value");
        assertEquals(401, errorResponse.getStatus(), "Getter for status should return updated value");
        assertEquals("/new/path", errorResponse.getPath(), "Getter for path should return updated value");
    }
}