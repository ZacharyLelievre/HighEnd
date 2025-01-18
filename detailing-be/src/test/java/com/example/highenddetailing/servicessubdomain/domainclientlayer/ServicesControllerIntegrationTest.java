package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
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
public class ServicesControllerIntegrationTest {

    @LocalServerPort
    private int port;  // This will hold the random port assigned to the embedded server

    @Autowired
    private ServiceRepository serviceRepository;  // The service repository for database interactions

    private RestTemplate restTemplate;  // RestTemplate for making HTTP requests

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();  // Initialize RestTemplate
    }

    @BeforeEach
    public void cleanDatabase() {
        serviceRepository.deleteAll(); // Ensure a clean database before each test
    }
    @BeforeEach
    public void initData() {
        // Insert mock data into the database (if needed)
        serviceRepository.deleteAll(); // Clear database to isolate tests
        serviceRepository.saveAll(Arrays.asList(
                new Service("Service 1", "Description of service 1", 100.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 2", "Description of service 2", 200.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 3", "Description of service 3", 300.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 4", "Description of service 4", 400.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 5", "Description of service 5", 500.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 6", "Description of service 6", 600.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 7", "Description of service 7", 700.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 8", "Description of service 8", 800.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 9", "Description of service 9", 900.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 10", "Description of service 10", 1000.00f, "resources/images/service/detailing-service-1.jpg")
        ));
    }

//    @Test
//    public void whenGetAllServices_thenReturnAllServices() {
//        // Construct the URL for the service
//        String url = "http://localhost:" + port + "/api/services";  // Use the random port assigned to the app
//
//        // Make a GET request to the API
//        ResponseEntity<List> response = restTemplate.exchange(
//                url,
//                org.springframework.http.HttpMethod.GET,
//                null,
//                List.class
//        );
//
//        // Assert that the response is OK and contains 10 services
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(10, response.getBody().size());
//    }


//    @Test
//    public void whenGetServiceById_thenReturnService() {
//        // Arrange: Get an existing service from the repository
//        Service service = serviceRepository.save(
//                new Service("Specific Service", "3 Hours", 300.00f, "resources/images/service/detailing-service-3.jpg")
//        );
//
//        String url = "http://localhost:" + port + "/api/services/" + service.getServiceIdentifier().getServiceId();
//
//        // Act: Make a GET request for the specific service
//        ResponseEntity<ServiceResponseModel> response = restTemplate.exchange(
//                url,
//                org.springframework.http.HttpMethod.GET,
//                null,
//                ServiceResponseModel.class
//        );
//
//        // Assert: Verify the service details
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        ServiceResponseModel serviceResponse = response.getBody();
//        assertEquals(service.getServiceIdentifier().getServiceId(), serviceResponse.getServiceId());
//        assertEquals(service.getServiceName(), serviceResponse.getServiceName());
//        assertEquals(service.getTimeRequired(), serviceResponse.getTimeRequired());
//        assertEquals(service.getPrice(), serviceResponse.getPrice());
//        assertEquals(service.getImagePath(), serviceResponse.getImagePath());
//    }


    // Add more tests for other endpoints or error cases if necessary...
}
