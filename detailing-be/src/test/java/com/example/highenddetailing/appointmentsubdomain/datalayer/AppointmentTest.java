package com.example.highenddetailing.appointmentsubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
        appointment1.setStatus("Scheduled");
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
        appointment2.setStatus("Scheduled");
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
        appointment.setStatus("Scheduled");
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
        Appointment appointment = new Appointment(1, "CUST123", "John Doe", "EMP001", "Jane Smith", "SERVICE001", "Car Wash", "2024-12-25", "10:30", "Scheduled", "/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("CUST123", appointment.getCustomerId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("EMP001", appointment.getEmployeeId());
        assertEquals("Jane Smith", appointment.getEmployeeName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals(LocalDate.of(2024, 12, 25), appointment.getAppointmentDate());
        assertEquals(LocalTime.of(10, 30), appointment.getAppointmentTime());
        assertEquals("Scheduled", appointment.getStatus());
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
                .status("Scheduled")
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
        appointment.setStatus("Scheduled");
        appointment.setImagePath("/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("John Doe", appointment.getCustomerName());
        assertEquals("Car Wash", appointment.getServiceName());
        assertEquals("Jane Smith", appointment.getEmployeeName());
    }
}