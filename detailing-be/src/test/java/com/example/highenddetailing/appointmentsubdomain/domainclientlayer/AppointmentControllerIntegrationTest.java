package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                        .customerName("John Doe") // Added customerName
                        .serviceId("SERVICE001") // Changed from ServiceIdentifier to String
                        .serviceName("Car Wash") // Added serviceName
                        .employeeId("EMP001")
                        .employeeName("Jane Smith") // Added employeeName
                        .appointmentDate(LocalDate.of(2024, 12, 25))
                        .appointmentTime(LocalTime.of(10, 30))
                        .status("Scheduled")
                        .imagePath("/images/appointment1.jpg")
                        .build(),

                Appointment.builder()
                        .id(null)
                        .appointmentIdentifier(new AppointmentIdentifier())
                        .customerId("CUST002")
                        .customerName("Michael Brown") // Added customerName
                        .serviceId("SERVICE002") // Changed from ServiceIdentifier to String
                        .serviceName("Brake Check") // Added serviceName
                        .employeeId("EMP002")
                        .employeeName("Emily White") // Added employeeName
                        .appointmentDate(LocalDate.of(2025, 1, 10))
                        .appointmentTime(LocalTime.of(14, 30))
                        .status("Completed")
                        .imagePath("/images/appointment2.jpg")
                        .build()
        ));
    }

    @Test
    public void whenGetAllAppointments_thenReturnAllAppointments() {
        String url = "http://localhost:" + port + "/api/appointments";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}