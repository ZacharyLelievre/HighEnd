package com.example.highenddetailing.appointmentsubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentServiceImpl;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
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
                new Appointment(1, new AppointmentIdentifier(), "c1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        new ServiceIdentifier(), "e1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        LocalDate.parse("2025-07-01"), LocalTime.parse("10:00:00"), "CONFIRMED", "detailing-service-1.jpg"),
                new Appointment(2, new AppointmentIdentifier(), "c1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        new ServiceIdentifier(), "e1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        LocalDate.parse("2025-07-02"), LocalTime.parse("11:00:00"), "CONFIRMED", "detailing-service-1.jpg")
        );

        List<AppointmentResponseModel> responseModels = List.of(
                new AppointmentResponseModel("a1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-01", "10:00:00",
                        "a1f14c90-ec5e-4f82-a9b7-2548a7325b34", "c1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "CONFIRMED", "detailing-service-1.jpg"),
                new AppointmentResponseModel("b1f14c90-ec5e-4f82-a9b7-2548a7325b34", "2025-07-02", "11:00:00",
                        "b5c64e99-9ac4-4f93-bb52-5c9ab7832e12", "c1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34", "CONFIRMED", "detailing-service-1.jpg")
        );

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModel(appointments)).thenReturn(responseModels);

        // Act
        List<AppointmentResponseModel> result = appointmentService.getAllAppointments();

        // Assert
        assertEquals(responseModels, result);
    }
}