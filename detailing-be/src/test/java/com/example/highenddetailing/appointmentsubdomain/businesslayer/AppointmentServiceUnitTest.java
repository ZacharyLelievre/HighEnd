package com.example.highenddetailing.appointmentsubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
                        "CONFIRMED", "detailing-service-1.jpg"
                ),
                new Appointment(2,
                        new AppointmentIdentifier(),
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "SERVICE002", "Brake Check", // serviceId, serviceName (changed from ServiceIdentifier to String)
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        LocalDate.parse("2025-07-02"), LocalTime.parse("11:00:00"),
                        "CONFIRMED", "detailing-service-1.jpg"
                )
        );

        List<AppointmentResponseModel> responseModels = List.of(
                new AppointmentResponseModel(
                        "a1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-01", "10:00:00",
                        "SERVICE001", "Car Wash", // serviceId, serviceName (updated)
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        "CONFIRMED", "detailing-service-1.jpg"
                ),
                new AppointmentResponseModel(
                        "b1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-02", "11:00:00",
                        "SERVICE002", "Brake Check", // serviceId, serviceName (updated)
                        "c1f14c90-ec5e-4f82-a9b7-2548a7325b34", "John Doe", // customerId, customerName
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "Jane Smith", // employeeId, employeeName
                        "CONFIRMED", "detailing-service-1.jpg"
                )
        );

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModel(appointments)).thenReturn(responseModels);

        // Act
        List<AppointmentResponseModel> result = appointmentService.getAllAppointments();

        // Assert
        assertEquals(responseModels, result);
    }
}