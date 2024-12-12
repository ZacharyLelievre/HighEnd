package com.example.highenddetailing.appointmentsubdomain;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AppointmentRepositoryIntegrationTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void whenFindAll_thenReturnAllAppointments() {
        // Arrange
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

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert: Verify the results
        assertNotNull(appointments);
        assertEquals(2, appointments.size());

        assertEquals(appointment1.getAppointmentDate(), appointments.get(0).getAppointmentDate());
        assertEquals(appointment1.getAppointmentTime(), appointments.get(0).getAppointmentTime());
        assertEquals(appointment1.getServiceId(), appointments.get(0).getServiceId()); // Assert for serviceId
        assertEquals(appointment1.getServiceName(), appointments.get(0).getServiceName()); // Assert for serviceName

        assertEquals(appointment2.getAppointmentDate(), appointments.get(1).getAppointmentDate());
        assertEquals(appointment2.getAppointmentTime(), appointments.get(1).getAppointmentTime());
        assertEquals(appointment2.getServiceId(), appointments.get(1).getServiceId()); // Assert for serviceId
        assertEquals(appointment2.getServiceName(), appointments.get(1).getServiceName()); // Assert for serviceName
    }
}