package com.example.highenddetailing.appointmentsubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AppointmentRepositoryIntegrationTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Test the findAll method
    @Test
    void whenFindAll_thenReturnAllAppointments() {
        // Arrange: Prepare sample appointments
        Appointment appointment1 = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier())
                .customerId("CUST001")
                .customerName("John Doe") // Added customerName
                .serviceId("SERVICE001") // Changed from ServiceIdentifier to String
                .serviceName("Car Wash") // Added serviceName
                .employeeId("EMP001")
                .employeeName("Jane Smith") // Added employeeName
                .appointmentDate(LocalDate.parse("2021-12-01"))
                .appointmentTime(LocalTime.parse("10:00"))
                .status(Status.CONFIRMED)
                .imagePath("/images/appointment1.jpg")
                .build();

        Appointment appointment2 = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier())
                .customerId("CUST002")
                .customerName("Michael Brown") // Added customerName
                .serviceId("SERVICE002") // Changed from ServiceIdentifier to String
                .serviceName("Brake Check") // Added serviceName
                .employeeId("EMP002")
                .employeeName("Emily White") // Added employeeName
                .appointmentDate(LocalDate.parse("2021-12-02"))
                .appointmentTime(LocalTime.parse("11:00"))
                .status(Status.CONFIRMED)
                .imagePath("/images/appointment2.jpg")
                .build();

        // Save the appointments in the database
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        // Act: Retrieve all appointments from the database
        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert: Verify the results
        assertNotNull(appointments);
        assertEquals(2, appointments.size());

        // Verify data for appointment 1
        assertEquals(appointment1.getAppointmentDate(), appointments.get(0).getAppointmentDate());
        assertEquals(appointment1.getAppointmentTime(), appointments.get(0).getAppointmentTime());
        assertEquals(appointment1.getServiceId(), appointments.get(0).getServiceId()); // Assert for serviceId
        assertEquals(appointment1.getServiceName(), appointments.get(0).getServiceName()); // Assert for serviceName
        assertEquals(appointment1.getCustomerName(), appointments.get(0).getCustomerName()); // Assert for customerName
        assertEquals(appointment1.getEmployeeName(), appointments.get(0).getEmployeeName()); // Assert for employeeName

        // Verify data for appointment 2
        assertEquals(appointment2.getAppointmentDate(), appointments.get(1).getAppointmentDate());
        assertEquals(appointment2.getAppointmentTime(), appointments.get(1).getAppointmentTime());
        assertEquals(appointment2.getServiceId(), appointments.get(1).getServiceId()); // Assert for serviceId
        assertEquals(appointment2.getServiceName(), appointments.get(1).getServiceName()); // Assert for serviceName
        assertEquals(appointment2.getCustomerName(), appointments.get(1).getCustomerName()); // Assert for customerName
        assertEquals(appointment2.getEmployeeName(), appointments.get(1).getEmployeeName()); // Assert for employeeName
    }

    @Test
    void whenDeleteAppointment_thenAppointmentIsRemoved() {
        // Arrange: Create and save an appointment
        String appointmentId = "c1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Appointment appointment = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier(appointmentId))
                .customerId("CUST003")
                .customerName("Sarah Johnson")
                .serviceId("SERVICE003")
                .serviceName("Oil Change")
                .employeeId("EMP003")
                .employeeName("James Brown")
                .appointmentDate(LocalDate.parse("2022-01-15"))
                .appointmentTime(LocalTime.parse("09:00"))
                .status(Status.PENDING)
                .imagePath("/images/appointment3.jpg")
                .build();

        appointmentRepository.save(appointment);

        // Act: Delete the appointment by finding it first
        Optional<Appointment> savedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId);
        assertTrue(savedAppointment.isPresent());

        appointmentRepository.delete(savedAppointment.get());

        // Assert: Verify the appointment is deleted
        Optional<Appointment> deletedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId);
        assertFalse(deletedAppointment.isPresent());
    }


}