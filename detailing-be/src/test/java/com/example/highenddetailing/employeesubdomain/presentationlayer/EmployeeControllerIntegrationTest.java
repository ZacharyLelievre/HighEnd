package com.example.highenddetailing.employeesubdomain.presentationlayer;


import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

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
        employeeRepository.deleteAll();
    }

    @BeforeEach
    public void initData() {
        // Properly initialize employee1 and employee2 first
        employee1 = Employee.builder()
                .id(null)
                .employeeIdentifier(new EmployeeIdentifier("e1f14c90-ec5e-4f82-a9b7-2548a7325b34"))
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("/images/employee1.jpg")
                .build();

        employee2 = Employee.builder()
                .id(null)
                .employeeIdentifier(new EmployeeIdentifier("e2f14c90-ec5e-4f82-a9b7-2548a7325b34"))
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
        String url = "http://localhost:" + port + "/api/employees";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

}