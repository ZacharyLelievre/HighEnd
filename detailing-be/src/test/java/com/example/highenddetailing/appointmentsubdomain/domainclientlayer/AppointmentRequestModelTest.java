package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentRequestModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentRequestModelTest {

    @Test
    void testAppointmentRequestModelBuilder() {
        // Arrange
        String appointmentDate = "2025-01-01";
        String appointmentTime = "10:00 AM";
        String appointmentEndTime = "11:00 AM";
        String serviceId = "SVC123";
        String serviceName = "Car Wash";
        String customerId = "CUST456";
        String customerName = "John Doe";
        String employeeId = "EMP789";
        String employeeName = "Jane Smith";

        // Act
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .appointmentDate(appointmentDate)
                .appointmentTime(appointmentTime)
                .appointmentEndTime(appointmentEndTime)
                .serviceId(serviceId)
                .serviceName(serviceName)
                .customerId(customerId)
                .customerName(customerName)
                .employeeId(employeeId)
                .employeeName(employeeName)
                .build();

        // Assert
        assertEquals(requestModel.getAppointmentDate(), appointmentDate);
        assertEquals(requestModel.getAppointmentTime(), appointmentTime);
        assertEquals(requestModel.getAppointmentEndTime(), appointmentEndTime);
        assertEquals(requestModel.getServiceId(), serviceId);
        assertEquals(requestModel.getServiceName(), serviceName);
        assertEquals(requestModel.getCustomerId(), customerId);
        assertEquals(requestModel.getCustomerName(), customerName);
        assertEquals(requestModel.getEmployeeId(), employeeId);
        assertEquals(requestModel.getEmployeeName(), employeeName);
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        AppointmentRequestModel requestModel = new AppointmentRequestModel();

        // Assert
        assertThat(requestModel.getAppointmentDate()).isNull();
        assertThat(requestModel.getAppointmentTime()).isNull();
        assertThat(requestModel.getAppointmentEndTime()).isNull();
        assertThat(requestModel.getServiceId()).isNull();
        assertThat(requestModel.getServiceName()).isNull();
        assertThat(requestModel.getCustomerId()).isNull();
        assertThat(requestModel.getCustomerName()).isNull();
        assertThat(requestModel.getEmployeeId()).isNull();
        assertThat(requestModel.getEmployeeName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String appointmentDate = "2025-01-01";
        String appointmentTime = "10:00 AM";
        String appointmentEndTime = "11:00 AM";
        String serviceId = "SVC123";
        String serviceName = "Car Wash";
        String customerId = "CUST456";
        String customerName = "John Doe";
        String employeeId = "EMP789";
        String employeeName = "Jane Smith";

        // Act
        AppointmentRequestModel requestModel = new AppointmentRequestModel(
                appointmentDate, appointmentTime, appointmentEndTime,
                serviceId, serviceName, customerId, customerName,
                employeeId, employeeName);

        // Assert
        assertEquals(requestModel.getAppointmentDate(), appointmentDate);
        assertEquals(requestModel.getAppointmentTime(), appointmentTime);
        assertEquals(requestModel.getAppointmentEndTime(), appointmentEndTime);
        assertEquals(requestModel.getServiceId(), serviceId);
        assertEquals(requestModel.getServiceName(), serviceName);
        assertEquals(requestModel.getCustomerId(), customerId);
        assertEquals(requestModel.getCustomerName(), customerName);
        assertEquals(requestModel.getEmployeeId(), employeeId);
        assertEquals(requestModel.getEmployeeName(), employeeName);
    }

    @Test
    void testSetters() {
        // Arrange
        AppointmentRequestModel requestModel = new AppointmentRequestModel();

        // Act
        requestModel.setAppointmentDate("2025-01-01");
        requestModel.setAppointmentTime("10:00 AM");
        requestModel.setAppointmentEndTime("11:00 AM");
        requestModel.setServiceId("SVC123");
        requestModel.setServiceName("Car Wash");
        requestModel.setCustomerId("CUST456");
        requestModel.setCustomerName("John Doe");
        requestModel.setEmployeeId("EMP789");
        requestModel.setEmployeeName("Jane Smith");

        // Assert
        assertThat(requestModel.getAppointmentDate()).isEqualTo("2025-01-01");
        assertThat(requestModel.getAppointmentTime()).isEqualTo("10:00 AM");
        assertThat(requestModel.getAppointmentEndTime()).isEqualTo("11:00 AM");
        assertThat(requestModel.getServiceId()).isEqualTo("SVC123");
        assertThat(requestModel.getServiceName()).isEqualTo("Car Wash");
        assertThat(requestModel.getCustomerId()).isEqualTo("CUST456");
        assertThat(requestModel.getCustomerName()).isEqualTo("John Doe");
        assertThat(requestModel.getEmployeeId()).isEqualTo("EMP789");
        assertThat(requestModel.getEmployeeName()).isEqualTo("Jane Smith");
    }

    @Test
    void testGetters() {
        // Arrange
        AppointmentRequestModel requestModel = new AppointmentRequestModel();
        requestModel.setAppointmentDate("2025-01-01");
        requestModel.setAppointmentTime("10:00 AM");
        requestModel.setAppointmentEndTime("11:00 AM");
        requestModel.setServiceId("SVC123");
        requestModel.setServiceName("Car Wash");
        requestModel.setCustomerId("CUST456");
        requestModel.setCustomerName("John Doe");
        requestModel.setEmployeeId("EMP789");
        requestModel.setEmployeeName("Jane Smith");

        // Act & Assert
        assertEquals("2025-01-01", requestModel.getAppointmentDate());
        assertEquals("10:00 AM", requestModel.getAppointmentTime());
        assertEquals("11:00 AM", requestModel.getAppointmentEndTime());
        assertEquals("SVC123", requestModel.getServiceId());
        assertEquals("Car Wash", requestModel.getServiceName());
        assertEquals("CUST456", requestModel.getCustomerId());
        assertEquals("John Doe", requestModel.getCustomerName());
        assertEquals("EMP789", requestModel.getEmployeeId());
        assertEquals("Jane Smith", requestModel.getEmployeeName());
    }

    @Test
    void testToString() {
        // Arrange
        AppointmentRequestModel.AppointmentRequestModelBuilder builder = AppointmentRequestModel.builder();
        builder.appointmentDate("2025-01-01")
                .appointmentTime("10:00 AM")
                .appointmentEndTime("11:00 AM")
                .serviceId("SVC123")
                .serviceName("Car Wash")
                .customerId("CUST456")
                .customerName("John Doe")
                .employeeId("EMP789")
                .employeeName("Jane Smith");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "appointmentDate=2025-01-01",
                "appointmentTime=10:00 AM",
                "appointmentEndTime=11:00 AM",
                "serviceId=SVC123",
                "serviceName=Car Wash",
                "customerId=CUST456",
                "customerName=John Doe",
                "employeeId=EMP789",
                "employeeName=Jane Smith"
        );
    }
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        AppointmentRequestModel requestModel1 = new AppointmentRequestModel(
                "2025-01-18", "10:00", "11:00", "service1", "Car Wash",
                "customer1", "John Doe", "employee1", "Alice Smith");

        AppointmentRequestModel requestModel2 = new AppointmentRequestModel(
                "2025-01-18", "10:00", "11:00", "service1", "Car Wash",
                "customer1", "John Doe", "employee1", "Alice Smith");

        AppointmentRequestModel requestModel3 = new AppointmentRequestModel(
                "2025-01-18", "11:00", "12:00", "service2", "Tire Change",
                "customer2", "Jane Smith", "employee2", "Bob Brown");

        // Act & Assert
        assertThat(requestModel1).isEqualTo(requestModel2);
        assertThat(requestModel1.hashCode()).isEqualTo(requestModel2.hashCode());

        assertThat(requestModel1).isNotEqualTo(requestModel3);
        assertThat(requestModel1.hashCode()).isNotEqualTo(requestModel3.hashCode());
    }
}