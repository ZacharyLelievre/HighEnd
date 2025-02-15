package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentController;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;

import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;

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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
//    @Test
//    @WithMockUser // Adds a mock authenticated user for Spring Security
//    void whenDeleteAppointment_thenReturnNoContentWithCsrf() throws Exception {
//        // Arrange
//        String appointmentId = "a1f14c90-ec5e-4f82-a9b7-2548a7325b34";
//
//        // Mock the service behavior
//        doNothing().when(appointmentService).deleteAppointment(appointmentId);
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/appointments/{id}", appointmentId)
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())  // Add CSRF token
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        // Verify the service interaction
//        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
//    }

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

//    @Test
//    @WithMockUser
//    void whenRescheduleAppointment_thenReturnUpdatedAppointment() throws Exception {
//        // Given: Mocked service response
//        AppointmentResponseModel updatedResponse = AppointmentResponseModel.builder()
//                .appointmentId("A001")
//                .appointmentDate("2024-12-26")
//                .appointmentTime("14:00")
//                .appointmentEndTime("15:00")
//                // ...set other fields as needed...
//                .build();
//
//        // Mock the behavior of appointmentService
//        when(appointmentService.rescheduleAppointment(
//                eq("A001"),  // expecting id
//                eq(LocalDate.parse("2024-12-26")),
//                eq(LocalTime.parse("14:00")),
//                eq(LocalTime.parse("15:00")))
//        ).thenReturn(updatedResponse);
//
//        // JSON request body to match RescheduleRequest fields in the controller
//        String requestBody = """
//        {
//          "newDate": "2024-12-26",
//          "newStartTime": "14:00",
//          "newEndTime": "15:00"
//        }
//        """;
//
//        // When: Perform the PUT request
//        mockMvc.perform(
//                        put("/api/appointments/{id}/reschedule", "A001")
//                                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(requestBody)
//                )
//                // Then: Expect a 200 response and JSON matching the updated appointment
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.appointmentId").value("A001"))
//                .andExpect(jsonPath("$.appointmentDate").value("2024-12-26"))
//                .andExpect(jsonPath("$.appointmentTime").value("14:00"))
//                .andExpect(jsonPath("$.appointmentEndTime").value("15:00"));
//
//        // Verify the service was called exactly once with the specified parameters
//        verify(appointmentService, times(1)).rescheduleAppointment(
//                "A001", LocalDate.parse("2024-12-26"),
//                LocalTime.parse("14:00"),
//                LocalTime.parse("15:00")
//        );
//    }

//    @Test
//    @WithMockUser
//    void whenRescheduleAppointmentConflict_thenReturn409() throws Exception {
//        // Force the service to throw a BookingConflictException
//        doThrow(new BookingConflictException("The new time slot is already booked."))
//                .when(appointmentService)
//                .rescheduleAppointment(eq("A001"),
//                        eq(LocalDate.parse("2024-12-26")),
//                        eq(LocalTime.parse("14:00")),
//                        eq(LocalTime.parse("15:00")));
//
//        String requestBody = """
//        {
//           "newDate": "2024-12-26",
//           "newStartTime": "14:00",
//           "newEndTime": "15:00"
//        }
//        """;
//
//        mockMvc.perform(put("/api/appointments/{id}/reschedule", "A001")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isConflict());
//
//        verify(appointmentService, times(1)).rescheduleAppointment(
//                "A001", LocalDate.parse("2024-12-26"),
//                LocalTime.parse("14:00"), LocalTime.parse("15:00"));
//    }
//
//    @Test
//    @WithMockUser
//    void whenGetAppointmentsByEmployeeId_thenReturnAppointments() throws Exception {
//        // Arrange: Mock the service to return a list of appointments for the given employee ID
//        when(appointmentService.getAppointmentsByEmployeeId(eq("E001"))).thenReturn(appointmentResponseModels);
//
//        // Act & Assert: Perform the GET request and check for the response
//        mockMvc.perform(get("/api/appointments/employee/{employeeId}", "E001")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2)) // Verify the number of appointments returned
//                .andExpect(jsonPath("$[0].appointmentId").value("A001")) // Check the first appointment's ID
//                .andExpect(jsonPath("$[1].appointmentId").value("A002")); // Check the second appointment's ID
//
//        // Verify the service interaction
//        verify(appointmentService, times(1)).getAppointmentsByEmployeeId("E001");
//    }


}