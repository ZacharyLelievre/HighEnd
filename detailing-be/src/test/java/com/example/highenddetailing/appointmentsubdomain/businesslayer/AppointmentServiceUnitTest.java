package com.example.highenddetailing.appointmentsubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.*;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentRequestModel;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentRequestMapper;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
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
    private AppointmentRequestMapper appointmentRequestMapper; // Added Mock

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
    void whenGetAppointmentByIdExists_thenReturnAppointmentResponseModel() {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";

        Appointment appointment = new Appointment(
                1,
                new AppointmentIdentifier(appointmentId),
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "SERVICE001", "Car Wash",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), LocalTime.parse("11:00:00"),
                Status.CONFIRMED, "detailing-service-1.jpg"
        );

        AppointmentResponseModel responseModel = new AppointmentResponseModel(
                appointmentId, "2025-07-01", "10:00:00", "11:00:00",
                "SERVICE001", "Car Wash",
                "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe",
                "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith",
                Status.CONFIRMED, "detailing-service-1.jpg"
        );

        // Mock repository and mapper behavior
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(Optional.of(appointment));
        when(appointmentResponseMapper.entityToResponseModel(appointment))
                .thenReturn(responseModel);

        // Act
        AppointmentResponseModel result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(responseModel, result, "The returned AppointmentResponseModel should match the expected model");
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentResponseMapper, times(1)).entityToResponseModel(appointment);
    }

    @Test
    void whenGetAppointmentByIdDoesNotExist_thenThrowRuntimeException() {
        // Arrange
        String invalidAppointmentId = "non-existent-id";

        // Mock repository behavior
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(invalidAppointmentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> appointmentService.getAppointmentById(invalidAppointmentId),
                "Expected getAppointmentById to throw, but it didn't"
        );

        assertEquals("Appointment not found: " + invalidAppointmentId, exception.getMessage(),
                "Exception message should match the expected message");
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(invalidAppointmentId);
        verify(appointmentResponseMapper, never()).entityToResponseModel(any());
    }
    @Test
    void whenCreateAppointmentWithAvailableTimeSlot_thenReturnAppointmentResponseModel() {
        // Arrange
        AppointmentRequestModel request = AppointmentRequestModel.builder()
                .appointmentDate("2025-07-01")
                .appointmentTime("10:00")
                .appointmentEndTime("11:00")
                .customerId("CUST123")
                .customerName("John Doe")
                .serviceId("SERVICE001")
                .serviceName("Car Wash")
                .employeeId("EMP001")
                .employeeName("Jane Smith")
                .build();

        // Mock isTimeSlotAvailable by mocking repository to return no overlapping appointments
        // Assuming isTimeSlotAvailable calls appointmentRepository.findOverlappingAppointments
        when(appointmentRepository.findOverlappingAppointments(
                LocalDate.parse(request.getAppointmentDate()),
                LocalTime.parse(request.getAppointmentTime()),
                LocalTime.parse(request.getAppointmentEndTime())
        )).thenReturn(List.of()); // No overlapping appointments

        // Mock appointmentRequestMapper to convert request to Appointment entity
        AppointmentIdentifier appointmentIdentifier = new AppointmentIdentifier("a1f14c90-ec5e-4f82-a9b7-2548a7325b34");
        Appointment appointmentEntity = new Appointment();
        appointmentEntity.setAppointmentIdentifier(appointmentIdentifier);
        appointmentEntity.setCustomerId(request.getCustomerId());
        appointmentEntity.setCustomerName(request.getCustomerName());
        appointmentEntity.setServiceId(request.getServiceId());
        appointmentEntity.setServiceName(request.getServiceName());
        appointmentEntity.setEmployeeId(request.getEmployeeId());
        appointmentEntity.setEmployeeName(request.getEmployeeName());
        appointmentEntity.setAppointmentDate(LocalDate.parse(request.getAppointmentDate()));
        appointmentEntity.setAppointmentTime(LocalTime.parse(request.getAppointmentTime()));
        appointmentEntity.setStatus(Status.PENDING);
        appointmentEntity.setImagePath("/images/appointment.jpg");

        when(appointmentRequestMapper.requestModelToEntity(eq(request), any(AppointmentIdentifier.class)))
                .thenReturn(appointmentEntity);

        // Mock appointmentRepository.save to return the saved appointment
        Appointment savedAppointment = new Appointment();
        savedAppointment.setAppointmentIdentifier(appointmentIdentifier);
        savedAppointment.setCustomerId(request.getCustomerId());
        savedAppointment.setCustomerName(request.getCustomerName());
        savedAppointment.setServiceId(request.getServiceId());
        savedAppointment.setServiceName(request.getServiceName());
        savedAppointment.setEmployeeId(request.getEmployeeId());
        savedAppointment.setEmployeeName(request.getEmployeeName());
        savedAppointment.setAppointmentDate(LocalDate.parse(request.getAppointmentDate()));
        savedAppointment.setAppointmentTime(LocalTime.parse(request.getAppointmentTime()));
        savedAppointment.setStatus(Status.PENDING);
        savedAppointment.setImagePath("/images/appointment.jpg");

        when(appointmentRepository.save(appointmentEntity)).thenReturn(savedAppointment);

        // Mock appointmentResponseMapper to convert Appointment entity to AppointmentResponseModel
        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId("a1f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .appointmentDate("2025-07-01")
                .appointmentTime("10:00:00")
                .appointmentEndTime("11:00:00")
                .serviceId("SERVICE001")
                .serviceName("Car Wash")
                .customerId("CUST123")
                .customerName("John Doe")
                .employeeId("EMP001")
                .employeeName("Jane Smith")
                .status(Status.PENDING)
                .imagePath("/images/appointment.jpg")
                .build();

        when(appointmentResponseMapper.entityToResponseModel(savedAppointment))
                .thenReturn(responseModel);

        // Act
        AppointmentResponseModel result = appointmentService.createAppointment(request);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(responseModel, result, "The returned AppointmentResponseModel should match the expected model");

        // Verify that appointmentRepository.findOverlappingAppointments was called
        verify(appointmentRepository, times(1)).findOverlappingAppointments(
                LocalDate.parse(request.getAppointmentDate()),
                LocalTime.parse(request.getAppointmentTime()),
                LocalTime.parse(request.getAppointmentEndTime())
        );

        // Verify that appointmentRequestMapper was called
        verify(appointmentRequestMapper, times(1))
                .requestModelToEntity(eq(request), any(AppointmentIdentifier.class));

        // Verify that appointmentRepository.save was called
        verify(appointmentRepository, times(1)).save(appointmentEntity);

        // Verify that appointmentResponseMapper was called
        verify(appointmentResponseMapper, times(1)).entityToResponseModel(savedAppointment);
    }

    // New Test for createAppointment - Booking Conflict
    @Test
    void whenCreateAppointmentWithConflictingTimeSlot_thenThrowBookingConflictException() {
        // Arrange
        AppointmentRequestModel request = AppointmentRequestModel.builder()
                .appointmentDate("2025-07-01")
                .appointmentTime("10:00")
                .appointmentEndTime("11:00")
                .customerId("CUST123")
                .customerName("John Doe")
                .serviceId("SERVICE001")
                .serviceName("Car Wash")
                .employeeId("EMP001")
                .employeeName("Jane Smith")
                .build();

        // Mock isTimeSlotAvailable by mocking repository to return overlapping appointments
        when(appointmentRepository.findOverlappingAppointments(
                LocalDate.parse(request.getAppointmentDate()),
                LocalTime.parse(request.getAppointmentTime()),
                LocalTime.parse(request.getAppointmentEndTime())
        )).thenReturn(List.of(
                new Appointment() // Simulate existing overlapping appointment
        ));

        // Act & Assert
        BookingConflictException exception = assertThrows(
                BookingConflictException.class,
                () -> appointmentService.createAppointment(request),
                "Expected createAppointment to throw BookingConflictException, but it didn't"
        );

        assertEquals("The time slot is already booked.", exception.getMessage(),
                "Exception message should match the expected message");

        // Verify that appointmentRepository.findOverlappingAppointments was called
        verify(appointmentRepository, times(1)).findOverlappingAppointments(
                LocalDate.parse(request.getAppointmentDate()),
                LocalTime.parse(request.getAppointmentTime()),
                LocalTime.parse(request.getAppointmentEndTime())
        );

        // Verify that appointmentRequestMapper was never called
        verify(appointmentRequestMapper, never())
                .requestModelToEntity(any(AppointmentRequestModel.class), any(AppointmentIdentifier.class));

        // Verify that appointmentRepository.save was never called
        verify(appointmentRepository, never()).save(any(Appointment.class));

        // Verify that appointmentResponseMapper was never called
        verify(appointmentResponseMapper, never()).entityToResponseModel(any(Appointment.class));
    }


}