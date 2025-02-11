package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // ensures the 'test' profile is used
public class AppointmentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @MockBean
    private AppointmentService appointmentService;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        appointmentRepository.deleteAll();
    }

    @BeforeEach
    public void initData() {
        appointmentRepository.saveAll(Arrays.asList(
                Appointment.builder()
                        .id(null)
                        .appointmentIdentifier(new AppointmentIdentifier())
                        .customerId("CUST001")
                        .customerName("John Doe")
                        .serviceId("SERVICE001")
                        .serviceName("Car Wash")
                        .employeeId("EMP001")
                        .employeeName("Jane Smith")
                        .appointmentDate(LocalDate.of(2024, 12, 25))
                        .appointmentTime(LocalTime.of(10, 30))
                        // FIX: Add an end time, e.g. 11:30
                        .appointmentEndTime(LocalTime.of(11, 30))
                        .status(Status.PENDING)
                        .imagePath("/images/appointment1.jpg")
                        .build()
        ));
    }

//    @Test
//    public void whenUpdateStatus_thenStatusIsUpdated() {
//        // Arrange
//        Appointment appointment = appointmentRepository.findAll().get(0);
//        String appointmentId = appointment.getAppointmentIdentifier().getAppointmentId();
//
//        String url = "http://localhost:" + port + "/api/appointments/" + appointmentId + "/status";
//
//        // Build the request body
//        String requestBody = """
//            {
//                "status": "CONFIRMED"
//            }
//        """;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        // Act
//        ResponseEntity<Appointment> response = restTemplate.exchange(
//                url,
//                HttpMethod.PUT,
//                requestEntity,
//                Appointment.class
//        );
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(Status.CONFIRMED, response.getBody().getStatus(), "Status should be updated to CONFIRMED");
//
//        // Verify the change was persisted to the database
//        Appointment updatedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId).orElse(null);
//        assertNotNull(updatedAppointment, "Updated appointment should exist in the database");
//        assertEquals(Status.CONFIRMED, updatedAppointment.getStatus(), "Status in the database should be CONFIRMED");
//    }

    @Test
    @WithMockUser  // or disable security in a @TestConfiguration
    void whenDeleteThrowsRuntimeException_then404NotFound() {
        // Arrange
        String appointmentId = "some-bad-id";

        // Force the service to throw a RuntimeException
        doThrow(new RuntimeException("Not found"))
                .when(appointmentService).deleteAppointment(appointmentId);

        // Act & Assert: Expect 404
        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.exchange(
                    "http://localhost:" + port + "/api/appointments/" + appointmentId,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
        });
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }



    @Test
    @WithMockUser  // or disable security in a @TestConfiguration
    void whenGetAppointmentsByEmployeeId_thenReturnsAppointments() {
        // Arrange
        String employeeId = "EMP001"; // Valid employeeId
        String url = "http://localhost:" + port + "/api/appointments/employee/" + employeeId;

        // Expected AppointmentResponseModel (you can mock the response if needed)
        AppointmentResponseModel expectedAppointment = AppointmentResponseModel.builder()
                .appointmentId(UUID.randomUUID().toString())
                .customerName("John Doe")
                .serviceName("Car Wash")
                .appointmentDate("2025-12-25")
                .appointmentTime("10:30")
                .appointmentEndTime("11:30")
                .status(Status.PENDING)
                .build();

        // Mocking the service to return the expected appointment response
        when(appointmentService.getAppointmentsByEmployeeId(employeeId))
                .thenReturn(Arrays.asList(expectedAppointment));

        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AppointmentResponseModel>>() {}
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty(), "Response should not be empty");
        assertEquals(expectedAppointment.getCustomerName(), response.getBody().get(0).getCustomerName());
    }



    @Test
    @WithMockUser
    void whenEmployeeIdNotFound_thenReturnsEmptyList() {
        // Arrange
        String employeeId = "EMP999"; // Non-existent employeeId
        String url = "http://localhost:" + port + "/api/appointments/employee/" + employeeId;

        // Mocking the service to return an empty list for a non-existent employeeId
        when(appointmentService.getAppointmentsByEmployeeId(employeeId)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AppointmentResponseModel>>() {}
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty(), "Response should be empty for non-existent employeeId");
    }

    @Test
    @WithMockUser
    void whenGetAppointmentsByCustomerId_thenReturnAppointments() {
        // Arrange
        String customerId = "CUST001";  // Valid customerId from test data
        String url = "http://localhost:" + port + "/api/appointments/customer/" + customerId;

        // Mocking expected appointment data
        AppointmentResponseModel expectedAppointment = AppointmentResponseModel.builder()
                .appointmentId(UUID.randomUUID().toString())
                .customerName("John Doe")
                .serviceName("Car Wash")
                .appointmentDate("2024-12-25")
                .appointmentTime("10:30")
                .appointmentEndTime("11:30")
                .status(Status.PENDING)
                .build();

        // Mock service behavior
        when(appointmentService.getAppointmentsByCustomerId(customerId))
                .thenReturn(Arrays.asList(expectedAppointment));

        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AppointmentResponseModel>>() {}
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertFalse(response.getBody().isEmpty(), "Response should contain appointments");
        assertEquals(expectedAppointment.getCustomerName(), response.getBody().get(0).getCustomerName());
        verify(appointmentService, times(1)).getAppointmentsByCustomerId(customerId);
    }

//    @Test
//    @WithMockUser
//    void whenGetAppointmentsByInvalidCustomerId_thenReturnEmptyList() {
//        // Arrange
//        String invalidCustomerId = "INVALID_CUST";
//        String url = "http://localhost:" + port + "/api/appointments/customer/" + invalidCustomerId;
//
//        // Mock service to return an empty list
//        when(appointmentService.getAppointmentsByCustomerId(invalidCustomerId)).thenReturn(Arrays.asList());
//
//        // Act
//        ResponseEntity<List<AppointmentResponseModel>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<AppointmentResponseModel>>() {}
//        );
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody(), "Response body should not be null");
//        assertTrue(response.getBody().isEmpty(), "Response should be empty for invalid customer ID");
//        verify(appointmentService, times(1)).getAppointmentsByCustomerId(invalidCustomerId);
//    }


}