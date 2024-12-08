package com.example.highenddetailing.appointmentsubdomain;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
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
                .appointmentDate(LocalDate.parse("2021-12-01"))
                .appointmentTime(LocalTime.parse("10:00"))
                .serviceId(new ServiceIdentifier())
                .build();

        Appointment appointment2 = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier())
                .appointmentDate(LocalDate.parse("2021-12-02"))
                .appointmentTime(LocalTime.parse("11:00"))
                .serviceId(new ServiceIdentifier())
                .build();

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert: Verify the results
        assertNotNull(appointments);
        assertEquals(appointments.size(), 2);

        assertEquals(appointment1.getAppointmentDate(), appointments.get(0).getAppointmentDate());
        assertEquals(appointment1.getAppointmentTime(), appointments.get(0).getAppointmentTime());

        assertEquals(appointment2.getAppointmentDate(), appointments.get(1).getAppointmentDate());
        assertEquals(appointment2.getAppointmentTime(), appointments.get(1).getAppointmentTime());
    }
}
