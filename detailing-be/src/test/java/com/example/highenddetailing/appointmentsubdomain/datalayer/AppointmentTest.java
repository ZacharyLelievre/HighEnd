package com.example.highenddetailing.appointmentsubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTest {

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAppointmentIdentifier(sharedIdentifier);
        appointment1.setServiceId(sharedServiceIdentifier);
        appointment1.setCustomerId("CUST123");
        appointment1.setEmployeeId("EMP001");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus("Scheduled");
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setAppointmentIdentifier(sharedIdentifier);
        appointment2.setServiceId(sharedServiceIdentifier);
        appointment2.setCustomerId("CUST123");
        appointment2.setEmployeeId("EMP001");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus("Scheduled");
        appointment2.setImagePath("/images/appointment1.jpg");

        Appointment appointment3 = new Appointment();
        appointment3.setId(2);
        appointment3.setAppointmentIdentifier(new AppointmentIdentifier());
        appointment3.setServiceId(new ServiceIdentifier());
        appointment3.setCustomerId("CUST456");
        appointment3.setEmployeeId("EMP002");
        appointment3.setAppointmentDate(LocalDate.of(2025, 1, 10));
        appointment3.setAppointmentTime(LocalTime.of(14, 30));
        appointment3.setStatus("Completed");
        appointment3.setImagePath("/images/appointment2.jpg");

        // Act & Assert
        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
        assertThat(appointment1).isNotEqualTo(appointment3);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAppointmentIdentifier(sharedIdentifier);
        appointment1.setServiceId(sharedServiceIdentifier);
        appointment1.setCustomerId("CUST123");
        appointment1.setEmployeeId("EMP001");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus("Scheduled");
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setAppointmentIdentifier(sharedIdentifier); // same
        appointment2.setServiceId(sharedServiceIdentifier);      // same
        appointment2.setCustomerId("CUST123");
        appointment2.setEmployeeId("EMP001");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus("Scheduled");
        appointment2.setImagePath("/images/appointment1.jpg");

        // Assertions for getters
        assertEquals(1, appointment1.getId());
        assertEquals(sharedIdentifier, appointment1.getAppointmentIdentifier());
        assertEquals(sharedServiceIdentifier, appointment1.getServiceId());
        assertEquals("CUST123", appointment1.getCustomerId());
        assertEquals("EMP001", appointment1.getEmployeeId());
        assertEquals(LocalDate.of(2024, 12, 25), appointment1.getAppointmentDate());
        assertEquals(LocalTime.of(10, 30), appointment1.getAppointmentTime());
        assertEquals("Scheduled", appointment1.getStatus());
        assertEquals("/images/appointment1.jpg", appointment1.getImagePath());

        // Equality check
        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
    }

    @Test
    void testCanEqual() {
        // Arrange
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAppointmentIdentifier(sharedIdentifier);
        appointment1.setServiceId(sharedServiceIdentifier);
        appointment1.setCustomerId("CUST123");
        appointment1.setEmployeeId("EMP001");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus("Scheduled");
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setAppointmentIdentifier(sharedIdentifier);
        appointment2.setServiceId(sharedServiceIdentifier);
        appointment2.setCustomerId("CUST123");
        appointment2.setEmployeeId("EMP001");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus("Scheduled");
        appointment2.setImagePath("/images/appointment1.jpg");

        // Act & Assert
        assertThat(appointment1.equals(appointment2)).isTrue();
        assertThat(appointment1.equals(new Object())).isFalse();
    }

    @Test
    void testConstructorWithAllArgs() {
        // This constructor creates new identifiers internally.
        Appointment appointment = new Appointment(1, "CUST123", "EMP001", "2024-12-25", "10:30", "Scheduled", "/images/appointment1.jpg");

        assertEquals(1, appointment.getId());
        assertEquals("CUST123", appointment.getCustomerId());
        assertEquals("EMP001", appointment.getEmployeeId());
        assertEquals(LocalDate.of(2024, 12, 25), appointment.getAppointmentDate());
        assertEquals(LocalTime.of(10, 30), appointment.getAppointmentTime());
        assertEquals("Scheduled", appointment.getStatus());
        assertEquals("/images/appointment1.jpg", appointment.getImagePath());
    }

    @Test
    void testAppointmentBuilderToString() {
        Appointment.AppointmentBuilder builder = Appointment.builder()
                .id(1)
                .customerId("CUST123")
                .employeeId("EMP001")
                .appointmentDate(LocalDate.of(2024, 12, 25))
                .appointmentTime(LocalTime.of(10, 30))
                .status("Scheduled")
                .imagePath("/images/appointment1.jpg");

        String toStringResult = builder.toString();

        assertThat(toStringResult).contains(
                "1",
                "CUST123",
                "EMP001",
                "2024-12-25",
                "10:30",
                "Scheduled",
                "/images/appointment1.jpg"
        );
    }

    @Test
    void testAppointmentBuilderId() {
        Appointment.AppointmentBuilder builder = Appointment.builder();
        builder.id(1);
        Appointment appointment = builder.build();
        assertThat(appointment.getId()).isEqualTo(1);
    }

    @Test
    void testAppointmentBuilderImagePath() {
        Appointment.AppointmentBuilder builder = Appointment.builder();
        builder.imagePath("/images/appointment1.jpg");
        Appointment appointment = builder.build();
        assertThat(appointment.getImagePath()).isEqualTo("/images/appointment1.jpg");
    }

    @Test
    void testAppointmentBuilderStatus() {
        Appointment.AppointmentBuilder builder = Appointment.builder();
        builder.status("Scheduled");
        Appointment appointment = builder.build();
        assertThat(appointment.getStatus()).isEqualTo("Scheduled");
    }

    @Test
    void testEquals() {
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAppointmentIdentifier(sharedIdentifier);
        appointment1.setServiceId(sharedServiceIdentifier);
        appointment1.setCustomerId("CUST123");
        appointment1.setEmployeeId("EMP001");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus("Scheduled");
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setAppointmentIdentifier(sharedIdentifier);
        appointment2.setServiceId(sharedServiceIdentifier);
        appointment2.setCustomerId("CUST123");
        appointment2.setEmployeeId("EMP001");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus("Scheduled");
        appointment2.setImagePath("/images/appointment1.jpg");

        Appointment appointment3 = new Appointment();
        appointment3.setId(2);
        appointment3.setAppointmentIdentifier(new AppointmentIdentifier());
        appointment3.setServiceId(new ServiceIdentifier());
        appointment3.setCustomerId("CUST456");
        appointment3.setEmployeeId("EMP002");
        appointment3.setAppointmentDate(LocalDate.of(2025, 1, 10));
        appointment3.setAppointmentTime(LocalTime.of(14, 30));
        appointment3.setStatus("Completed");
        appointment3.setImagePath("/images/appointment2.jpg");

        assertThat(appointment1).isEqualTo(appointment2);
        assertThat(appointment1).isNotEqualTo(appointment3);
    }

    @Test
    void testHashCode() {
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAppointmentIdentifier(sharedIdentifier);
        appointment1.setServiceId(sharedServiceIdentifier);
        appointment1.setCustomerId("CUST123");
        appointment1.setEmployeeId("EMP001");
        appointment1.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment1.setAppointmentTime(LocalTime.of(10, 30));
        appointment1.setStatus("Scheduled");
        appointment1.setImagePath("/images/appointment1.jpg");

        Appointment appointment2 = new Appointment();
        appointment2.setId(1);
        appointment2.setAppointmentIdentifier(sharedIdentifier);
        appointment2.setServiceId(sharedServiceIdentifier);
        appointment2.setCustomerId("CUST123");
        appointment2.setEmployeeId("EMP001");
        appointment2.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment2.setAppointmentTime(LocalTime.of(10, 30));
        appointment2.setStatus("Scheduled");
        appointment2.setImagePath("/images/appointment1.jpg");

        Appointment appointment3 = new Appointment();
        appointment3.setId(2);
        appointment3.setAppointmentIdentifier(new AppointmentIdentifier());
        appointment3.setServiceId(new ServiceIdentifier());
        appointment3.setCustomerId("CUST456");
        appointment3.setEmployeeId("EMP002");
        appointment3.setAppointmentDate(LocalDate.of(2025, 1, 10));
        appointment3.setAppointmentTime(LocalTime.of(14, 30));
        appointment3.setStatus("Completed");
        appointment3.setImagePath("/images/appointment2.jpg");

        assertThat(appointment1.hashCode()).isEqualTo(appointment2.hashCode());
        assertThat(appointment1.hashCode()).isNotEqualTo(appointment3.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        AppointmentIdentifier sharedIdentifier = new AppointmentIdentifier();
        ServiceIdentifier sharedServiceIdentifier = new ServiceIdentifier();

        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setAppointmentIdentifier(sharedIdentifier);
        appointment.setServiceId(sharedServiceIdentifier);
        appointment.setCustomerId("CUST123");
        appointment.setEmployeeId("EMP001");
        appointment.setAppointmentDate(LocalDate.of(2024, 12, 25));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus("Scheduled");
        appointment.setImagePath("/images/appointment1.jpg");

        assertThat(appointment.getId()).isEqualTo(1);
        assertThat(appointment.getAppointmentIdentifier()).isEqualTo(sharedIdentifier);
        assertThat(appointment.getServiceId()).isEqualTo(sharedServiceIdentifier);
        assertThat(appointment.getCustomerId()).isEqualTo("CUST123");
        assertThat(appointment.getEmployeeId()).isEqualTo("EMP001");
        assertThat(appointment.getAppointmentDate()).isEqualTo(LocalDate.of(2024, 12, 25));
        assertThat(appointment.getAppointmentTime()).isEqualTo(LocalTime.of(10, 30));
        assertThat(appointment.getStatus()).isEqualTo("Scheduled");
        assertThat(appointment.getImagePath()).isEqualTo("/images/appointment1.jpg");
    }
}