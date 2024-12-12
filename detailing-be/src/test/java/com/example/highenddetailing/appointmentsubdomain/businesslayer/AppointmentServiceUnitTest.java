package com.example.highenddetailing.appointmentsubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceUnitTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void whenGetAllAppointments_thenReturnAllAppointments() {
        // Arrange
        List<Appointment> appointments = List.of(
                new Appointment(1,
                        new AppointmentIdentifier(),
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "SERVICE001", "Car Wash", // serviceId, serviceName (changed from ServiceIdentifier to String)
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"),
                        Status.CONFIRMED, "detailing-service-1.jpg"
                ),
                new Appointment(2,
                        new AppointmentIdentifier(),
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "SERVICE002", "Brake Check", // serviceId, serviceName (changed from ServiceIdentifier to String)
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        LocalDate.parse("2025-07-02"), LocalTime.parse("11:00:00"),
                        Status.CONFIRMED, "detailing-service-1.jpg"
                )
        );

        List<AppointmentResponseModel> responseModels = List.of(
                new AppointmentResponseModel(
                        "a1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-01", "10:00:00",
                        "SERVICE001", "Car Wash", // serviceId, serviceName (updated)
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        Status.CONFIRMED, "detailing-service-1.jpg"
                ),
                new AppointmentResponseModel(
                        "b1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-02", "11:00:00",
                        "SERVICE002", "Brake Check", // serviceId, serviceName (updated)
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        Status.CONFIRMED, "detailing-service-1.jpg"
                )
        );

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModel(appointments)).thenReturn(responseModels);

        // Act
        List<AppointmentResponseModel> result = appointmentService.getAllAppointments();

        // Assert
        assertEquals(responseModels, result);
    }
    @Test
    void whenUpdateStatus_thenStatusIsUpdatedAndSaved() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Status newStatus = Status.CONFIRMED;

        // Create a mock Appointment with the initial status PENDING
        Appointment existingAppointment = new Appointment(
                1,
                new AppointmentIdentifier(),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"),
                Status.PENDING,
                "detailing-service-1.jpg"
        );

        // Create an updated appointment with the new status
        Appointment updatedAppointment = new Appointment(
                1,
                new AppointmentIdentifier(),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"),
                newStatus,
                "detailing-service-1.jpg"
        );

        // Mock the behavior of the repository
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(existingAppointment));

        when(appointmentRepository.save(existingAppointment))
                .thenReturn(updatedAppointment);

        // Act
        Appointment result = appointmentService.updateStatus(appointmentId, newStatus);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(newStatus, result.getStatus(), "The status should be updated to CONFIRMED");

        // Verify that the save method was called
        verify(appointmentRepository, times(1)).save(existingAppointment);

        // Verify that the status was updated before saving
        assertEquals(newStatus, existingAppointment.getStatus(), "The status of the existing appointment should be updated");
    }
}