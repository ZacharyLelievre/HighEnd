package com.example.highenddetailing.employeesubdomain.presentationlayer;


import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        // Clear out any pre-existing data
        employeeRepository.deleteAll();
    }

    @BeforeEach
    public void initData() {
        // Create Employee #1
        employee1 = Employee.builder()
                .employeeId("e1f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("/images/employee1.jpg")
                .build();

        // Create Employee #2
        employee2 = Employee.builder()
                .employeeId("e2f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("2323232")
                .salary(55000.00)
                .imagePath("/images/employee2.jpg")
                .build();

        // Save employees to the repository
        employeeRepository.saveAll(Arrays.asList(employee1, employee2));
    }

    @Test
    public void whenGetAllEmployees_thenReturnAllEmployees() {
        // Example test to fetch all employees
        String url = "http://localhost:" + port + "/api/employees";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // We saved two in initData, so we expect 2 back
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAvailability_ShouldReturnAvailabilityForEmployee() throws Exception {
        // Arrange: create a new employee with availability
        String employeeId = "e3f14c90-ec5e-4f82-a9b7-2548a7325b34"; // a different test ID
        Employee employee = Employee.builder()
                .employeeId(employeeId)
                .first_name("Emily")
                .last_name("Brown")
                .position("Supervisor")
                .email("emily.brown@example.com")
                .phone("123456789")
                .salary(68000.00)
                .imagePath("profile.png")
                .availability(List.of(
                        new Availability("Monday", "08:00", "12:00"),
                        new Availability("Wednesday", "09:00", "17:00")
                ))
                .build();
        employeeRepository.save(employee);

        // Act & Assert: call GET /api/employees/{employeeId}/availability
        mockMvc.perform(get("/api/employees/{employeeId}/availability", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].dayOfWeek").value("Monday"))
                .andExpect(jsonPath("$[0].startTime").value("08:00"))
                .andExpect(jsonPath("$[0].endTime").value("12:00"))
                .andExpect(jsonPath("$[1].dayOfWeek").value("Wednesday"))
                .andExpect(jsonPath("$[1].startTime").value("09:00"))
                .andExpect(jsonPath("$[1].endTime").value("17:00"));
    }

}