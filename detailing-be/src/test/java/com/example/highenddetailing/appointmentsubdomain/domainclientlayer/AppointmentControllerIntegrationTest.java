package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AppointmentRepository appointmentRepository;

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
                        .status(Status.PENDING)
                        .imagePath("/images/appointment1.jpg")
                        .build()
        ));
    }

    @Test
    public void whenUpdateStatus_thenStatusIsUpdated() {
        // Arrange
        Appointment appointment = appointmentRepository.findAll().get(0);
        String appointmentId = appointment.getAppointmentIdentifier().getAppointmentId();

        String url = "http://localhost:" + port + "/api/appointments/" + appointmentId + "/status";

        // Build the request body
        String requestBody = """
            {
                "status": "CONFIRMED"
            }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Act
        ResponseEntity<Appointment> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Appointment.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Status.CONFIRMED, response.getBody().getStatus(), "Status should be updated to CONFIRMED");

        // Verify the change was persisted to the database
        Appointment updatedAppointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId).orElse(null);
        assertNotNull(updatedAppointment, "Updated appointment should exist in the database");
        assertEquals(Status.CONFIRMED, updatedAppointment.getStatus(), "Status in the database should be CONFIRMED");
    }
}