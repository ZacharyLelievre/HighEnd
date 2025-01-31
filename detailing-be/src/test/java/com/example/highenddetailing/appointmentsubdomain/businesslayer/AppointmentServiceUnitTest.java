package com.example.highenddetailing.appointmentsubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.*;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceUnitTest {

    List<Availability> availabilityList = new ArrayList<>();

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void whenDeleteExistingAppointment_thenDeleteSuccessfully() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Appointment existingAppointment = new Appointment();
        existingAppointment.setAppointmentIdentifier(new AppointmentIdentifier(appointmentId));

        // Mock repository behavior
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        doNothing().when(appointmentRepository).delete(existingAppointment);

        // Act
        appointmentService.deleteAppointment(appointmentId);

        // Assert
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentRepository, times(1)).delete(existingAppointment);
    }

    @Test
    void whenDeleteNonExistentAppointment_thenThrowRuntimeException() {
        // Arrange
        String invalidAppointmentId = "non-existent-id";

        // Mock repository to return empty Optional
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(invalidAppointmentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> appointmentService.deleteAppointment(invalidAppointmentId)
        );

        assertEquals("Appointment not found with id: " + invalidAppointmentId, exception.getMessage());
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(invalidAppointmentId);
        verify(appointmentRepository, never()).delete(any(Appointment.class));
    }
    @Test
    void whenGetAllAppointments_thenReturnAllAppointments() {
        // Arrange (Original)
        List<Appointment> appointments = List.of(
                new Appointment(1,
                        new AppointmentIdentifier(),
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                        "SERVICE001", "Car Wash",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                        LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                        Status.CONFIRMED, "detailing-service-1.jpg"
                ),
                new Appointment(2,
                        new AppointmentIdentifier(),
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                        "SERVICE002", "Brake Check",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                        LocalDate.parse("2025-07-02"), LocalTime.parse("11:00:00"), LocalTime.parse("12:00:00"),
                        Status.CONFIRMED, "detailing-service-1.jpg"
                )
        );

        List<AppointmentResponseModel> responseModels = List.of(
                new AppointmentResponseModel(
                        "a1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-01", "10:00:00", "11:00:00",
                        "SERVICE001", "Car Wash",
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                        Status.CONFIRMED, "detailing-service-1.jpg"
                ),
                new AppointmentResponseModel(
                        "b1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-02", "11:00:00", "12:00:00",
                        "SERVICE002", "Brake Check",
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
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
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                Status.PENDING,
                "detailing-service-1.jpg"
        );

        Appointment updatedAppointment = new Appointment(
                1,
                new AppointmentIdentifier(),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                newStatus,
                "detailing-service-1.jpg"
        );

        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(existingAppointment))
                .thenReturn(updatedAppointment);

        // Act (Original)
        Appointment result = appointmentService.updateStatus(appointmentId, newStatus);

        // Assert (Original)
        assertNotNull(result, "The result should not be null");
        assertEquals(newStatus, result.getStatus(), "The status should be updated to CONFIRMED");
        verify(appointmentRepository, times(1)).save(existingAppointment);
        assertEquals(newStatus, existingAppointment.getStatus(), "The status of the existing appointment should be updated");
    }

//    @Test
//    void whenAssignEmployee_thenEmployeeIsAssignedSuccessfully() {
//        // Arrange
//        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
//        String employeeId = "e1f14c90-ec5e-4f82-a9b7-2548a7325b34";
//        EmployeeRequestModel request = new EmployeeRequestModel(employeeId, null); // first_name is not used
//
//        // Mock Appointment with specific AppointmentIdentifier
//        Appointment appointmentToAssign = new Appointment(
//                1,
//                new AppointmentIdentifier(appointmentId),
//                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
//                "SERVICE001", "Car Wash",
//                null, null, // Employee not assigned yet
//                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
//                Status.PENDING,
//                "detailing-service-1.jpg"
//        );
//
//        // Mock Employee with specific EmployeeIdentifier
////        Employee employeeToAssign = new Employee(
////                //new EmployeeIdentifier(employeeId),
////                "John",
////                "Smith",
////                "Technician",
////                "john.smith@example.com",
////                "123-456-7890",
////                50000.0,
////                "johndoe.jpg",
////                availabilityList
////        );
//
//        // Mock the response model after assignment
//        AppointmentResponseModel expectedResponse = new AppointmentResponseModel(
//                appointmentId, "2025-07-01", "10:00:00", "11:00:00",
//                "SERVICE001", "Car Wash",
//                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
//                employeeId, "John Smith",
//                Status.PENDING, "detailing-service-1.jpg"
//        );
//
//        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
//                .thenReturn(Optional.of(appointmentToAssign));
//
//        when(employeeRepository.findByEmployeeId(employeeId))
//                .thenReturn(Optional.of(employeeToAssign));
//
//        when(appointmentRepository.save(appointmentToAssign))
//                .thenReturn(appointmentToAssign);
//
//        when(appointmentResponseMapper.entityToResponseModel(appointmentToAssign))
//                .thenReturn(expectedResponse);
//
//        // Act
//        AppointmentResponseModel result = appointmentService.assignEmployee(appointmentId, request);
//
//        // Assert
//        assertNotNull(result, "The result should not be null");
//        assertEquals("John Smith", appointmentToAssign.getEmployeeName(), "Employee name should be updated correctly");
//        assertEquals(employeeId, appointmentToAssign.getEmployeeId(), "Employee ID should be updated correctly");
//        verify(appointmentRepository, times(1)).save(appointmentToAssign);
//    }

    @Test
    void whenAppointmentNotFound_thenThrowRuntimeException() {
        // Arrange
        String invalidId = "invalid-id";
        EmployeeRequestModel request = new EmployeeRequestModel(
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", // employeeId
                "John",                                  // first_name
                "Doe",                                   // last_name
                "Manager",                               // position
                "john.doe@example.com",                  // email
                "123-456-7890",                          // phone
                75000.0,                                 // salary
                "/images/john-doe.jpg"                   // imagePath
        );

        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(invalidId))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> appointmentService.assignEmployee(invalidId, request)
        );

        assertEquals("Appointment not found with id: invalid-id", exception.getMessage());
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(invalidId);
    }

    @Test
    void whenEmployeeNotFound_thenThrowRuntimeException() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        EmployeeRequestModel request = new EmployeeRequestModel(
                "non-existent-id",       // employeeId
                "Jane",                  // first_name
                "Doe",                   // last_name
                "Technician",            // position
                "jane.doe@example.com",  // email
                "987-654-3210",          // phone
                50000.0,                 // salary
                "/images/jane-doe.jpg"   // imagePath
        );

        Appointment existingAppointment = new Appointment(
                1,
                new AppointmentIdentifier(),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                null, null,
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                Status.PENDING,
                "detailing-service-1.jpg"
        );

        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        when(employeeRepository.findByEmployeeId("non-existent-id"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> appointmentService.assignEmployee(appointmentId, request)
        );

        assertEquals("Employee not found with id: non-existent-id", exception.getMessage());
        verify(appointmentRepository, times(1))
                .findByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(employeeRepository, times(1))
                .findByEmployeeId("non-existent-id");
    }

    @Test
    void whenRescheduleAppointmentWithNoConflicts_thenUpdateAppointmentSuccessfully() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        LocalDate newDate = LocalDate.parse("2025-07-03");
        LocalTime newStartTime = LocalTime.parse("12:00:00");
        LocalTime newEndTime = LocalTime.parse("13:00:00");

        // Mock existing appointment
        Appointment existingAppointment = new Appointment(
                1,
                new AppointmentIdentifier(appointmentId),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                Status.PENDING,
                "detailing-service-1.jpg"
        );

        // Mock repository responses
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.findOverlappingAppointmentsExcludingCurrent(appointmentId, newDate, newStartTime, newEndTime))
                .thenReturn(List.of());  // No conflicts

        // Mock the updated appointment response
        Appointment updatedAppointment = new Appointment(
                1,
                new AppointmentIdentifier(appointmentId),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                newDate, newStartTime, newEndTime,
                Status.PENDING,
                "detailing-service-1.jpg"
        );

        when(appointmentRepository.save(existingAppointment)).thenReturn(updatedAppointment);
        when(appointmentResponseMapper.entityToResponseModel(updatedAppointment))
                .thenReturn(new AppointmentResponseModel(
                        appointmentId, newDate.toString(), newStartTime.toString(), newEndTime.toString(),
                        "SERVICE001", "Car Wash",
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                        Status.PENDING, "detailing-service-1.jpg"
                ));

        // Act
        AppointmentResponseModel result = appointmentService.rescheduleAppointment(appointmentId, newDate, newStartTime, newEndTime);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(newDate.toString(), result.getAppointmentDate(), "The date should be updated correctly");
        assertEquals(newStartTime.toString(), result.getAppointmentTime(), "The start time should be updated correctly");
        assertEquals(newEndTime.toString(), result.getAppointmentEndTime(), "The end time should be updated correctly");
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }

    @Test
    void whenRescheduleAppointmentWithConflict_thenThrowBookingConflictException() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        LocalDate newDate = LocalDate.parse("2025-07-03");
        LocalTime newStartTime = LocalTime.parse("12:00:00");
        LocalTime newEndTime = LocalTime.parse("13:00:00");

        Appointment conflictingAppointment = new Appointment(
                2,
                new AppointmentIdentifier(),
                "another-customer-id", "Alice Doe",
                "SERVICE002", "Brake Check",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Bob Smith",
                newDate, newStartTime, newEndTime,
                Status.PENDING,
                "detailing-service-2.jpg"
        );

        when(appointmentRepository.findOverlappingAppointmentsExcludingCurrent(appointmentId, newDate, newStartTime, newEndTime))
                .thenReturn(List.of(conflictingAppointment));

        // Act & Assert
        BookingConflictException exception = assertThrows(
                BookingConflictException.class,
                () -> appointmentService.rescheduleAppointment(appointmentId, newDate, newStartTime, newEndTime)
        );

        assertEquals("The new time slot is already booked.", exception.getMessage());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

}