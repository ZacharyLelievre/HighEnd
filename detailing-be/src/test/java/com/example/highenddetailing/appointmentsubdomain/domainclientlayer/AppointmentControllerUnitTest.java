package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentController;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc; // Simulates HTTP requests

    @MockBean
    private AppointmentService appointmentService; // Mock AppointmentService

    @MockBean
    private AppointmentResponseMapper appointmentResponseMapper; // Mock AppointmentResponseMapper
    private List<AppointmentResponseModel> appointmentResponseModels;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        appointmentResponseModels = List.of(
                AppointmentResponseModel.builder()
                        .appointmentId("A001")
                        .customerId("C001")
                        .serviceId("S001")
                        .employeeId("E001")
                        .appointmentDate("2023-12-31")
                        .appointmentTime("10:00")
                        .status(Status.CONFIRMED)
                        .imagePath("/images/detailing.jpg")
                        .build(),
                AppointmentResponseModel.builder()
                        .appointmentId("A002")
                        .customerId("C002")
                        .serviceId("S002")
                        .employeeId("E002")
                        .appointmentDate("2024-01-10")
                        .appointmentTime("14:30")
                        .status(Status.CONFIRMED)
                        .imagePath("/images/detailing2.jpg")
                        .build()
        );
    }
    @Test
    @WithMockUser // Adds a mock authenticated user for Spring Security
    void whenDeleteAppointment_thenReturnNoContentWithCsrf() throws Exception {
        // Arrange
        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";

        // Mock the service behavior
        doNothing().when(appointmentService).deleteAppointment(appointmentId);

        // Act & Assert
        mockMvc.perform(delete("/api/appointments/{id}", appointmentId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())  // Add CSRF token
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the service interaction
        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
    }

//    @Test
//    void testGetAllAppointments_EmptyList() throws Exception {
//        // Arrange: Mock the service to return an empty list
//        when(appointmentService.getAllAppointments()).thenReturn(List.of());
//
//        // Act & Assert: Perform the GET request and check for an empty response
//        mockMvc.perform(get("/api/appointments")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//    }

}