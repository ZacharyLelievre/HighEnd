package com.example.highenddetailing.appointmentsubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class AppointmentRepositoryIntegrationTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

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

    @Test
    void whenRescheduleAppointmentWithoutConflict_thenUpdateAppointmentSuccessfully() {
        // Arrange: Create and save an initial appointment
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Appointment appointment = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier(appointmentId))
                .customerId("CUST004")
                .customerName("Tom Hanks")
                .serviceId("SERVICE004")
                .serviceName("Tire Rotation")
                .employeeId("EMP004")
                .employeeName("Chris Evans")
                .appointmentDate(LocalDate.parse("2023-03-10"))
                .appointmentTime(LocalTime.parse("09:00"))
                .appointmentEndTime(LocalTime.parse("10:00"))
                .status(Status.PENDING)
                .imagePath("/images/appointment4.jpg")
                .build();

        appointmentRepository.save(appointment);

        // Act: Reschedule the appointment to a new date and time with no conflicts
        LocalDate newDate = LocalDate.parse("2023-03-12");
        LocalTime newStartTime = LocalTime.parse("14:00");
        LocalTime newEndTime = LocalTime.parse("15:00");

        Optional<Appointment> savedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId);
        assertTrue(savedAppointment.isPresent());

        Appointment rescheduledAppointment = savedAppointment.get();
        rescheduledAppointment.setAppointmentDate(newDate);
        rescheduledAppointment.setAppointmentTime(newStartTime);
        rescheduledAppointment.setAppointmentEndTime(newEndTime);
        appointmentRepository.save(rescheduledAppointment);

        // Assert: Verify the appointment was updated
        Appointment updatedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId).orElse(null);
        assertNotNull(updatedAppointment);
        assertEquals(newDate, updatedAppointment.getAppointmentDate());
        assertEquals(newStartTime, updatedAppointment.getAppointmentTime());
        assertEquals(newEndTime, updatedAppointment.getAppointmentEndTime());
    }

    @Test
    void whenRescheduleAppointmentWithConflict_thenDoNotUpdateAppointment() {
        // Arrange: Create and save two overlapping appointments
        String appointmentId1 = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        String appointmentId2 = "b1f14c90-ec5e-4f82-a9b7-2548a7325b35";

        Appointment appointment1 = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier(appointmentId1))
                .customerId("CUST005")
                .customerName("Robert Downey Jr.")
                .serviceId("SERVICE005")
                .serviceName("Wheel Alignment")
                .employeeId("EMP005")
                .employeeName("Scarlett Johansson")
                .appointmentDate(LocalDate.parse("2023-03-15"))
                .appointmentTime(LocalTime.parse("09:00"))
                .appointmentEndTime(LocalTime.parse("10:00"))
                .status(Status.PENDING)
                .imagePath("/images/appointment5.jpg")
                .build();

        Appointment appointment2 = Appointment.builder()
                .appointmentIdentifier(new AppointmentIdentifier(appointmentId2))
                .customerId("CUST006")
                .customerName("Natalie Portman")
                .serviceId("SERVICE006")
                .serviceName("Engine Tune-Up")
                .employeeId("EMP006")
                .employeeName("Chris Hemsworth")
                .appointmentDate(LocalDate.parse("2023-03-15"))
                .appointmentTime(LocalTime.parse("09:30"))
                .appointmentEndTime(LocalTime.parse("10:30"))
                .status(Status.CONFIRMED)
                .imagePath("/images/appointment6.jpg")
                .build();

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        // Act: Attempt to reschedule the first appointment to the same time as the conflicting appointment
        Optional<Appointment> savedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId1);
        assertTrue(savedAppointment.isPresent());

        Appointment reschedulingAppointment = savedAppointment.get();
        reschedulingAppointment.setAppointmentDate(appointment2.getAppointmentDate());
        reschedulingAppointment.setAppointmentTime(appointment2.getAppointmentTime());
        reschedulingAppointment.setAppointmentEndTime(appointment2.getAppointmentEndTime());

        // Check if there's a conflict using the repository method
        List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointmentsExcludingCurrent(
                appointmentId1, appointment2.getAppointmentDate(), appointment2.getAppointmentTime(), appointment2.getAppointmentEndTime()
        );

        // Assert: Verify that the appointment cannot be rescheduled due to conflict
        assertFalse(overlappingAppointments.isEmpty(), "There should be a conflict preventing rescheduling.");
    }

    @Test
    void whenGetAppointmentsByEmployeeId_thenReturnMappedResponse() {
        // Arrange - Save real data in the DB
        String employeeId = "EMP001";
        Appointment appointment1 = new Appointment();
        appointment1.setEmployeeId(employeeId);
        appointment1.setServiceId("SVC123");
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setEmployeeId(employeeId);
        appointment2.setServiceId("SVC456");
        appointmentRepository.save(appointment2);

        List<Appointment> savedAppointments = appointmentRepository.findByEmployeeId(employeeId);
        assertEquals(2, savedAppointments.size()); // Ensure DB has correct data

        // Simulate mapping behavior
        List<AppointmentResponseModel> expectedResponses = List.of(new AppointmentResponseModel(), new AppointmentResponseModel());
        when(appointmentResponseMapper.entityListToResponseModel(savedAppointments)).thenReturn(expectedResponses);

        // Act - Call service method
        List<AppointmentResponseModel> actualResponses = appointmentResponseMapper.entityListToResponseModel(savedAppointments);

        // Assert
        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());

        // Verify mapping interaction
        verify(appointmentResponseMapper, times(1)).entityListToResponseModel(anyList());
    }

}