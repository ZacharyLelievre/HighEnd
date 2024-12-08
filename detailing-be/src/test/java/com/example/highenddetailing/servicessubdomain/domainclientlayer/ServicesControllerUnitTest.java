package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.businesslayer.ServiceService;
import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServicesController.class)
public class ServicesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc; // Inject MockMvc to simulate HTTP requests

    @MockBean
    private ServiceService serviceService; // Mock the ServiceService

    private List<Service> services;
    private List<ServiceResponseModel> serviceResponseModels;

    @BeforeEach
    public void setup() {
        services = Arrays.asList(
                new Service("Service 1", "Description of service 1", 100.00f, "resources/images/service/detailing-service-1.jpg"),
                new Service("Service 2", "Description of service 2", 200.00f, "resources/images/service/detailing-service-1.jpg")
        );

        serviceResponseModels = Arrays.asList(
                ServiceResponseModel.builder()
                    .serviceId("1")  // Example ID
                    .serviceName("Service 1")  // Example Name
                    .timeRequired("3 hours")  // Example Description
                    .price(100.00f)  // Example Price
                    .build(),
                ServiceResponseModel.builder()
                    .serviceId("2")
                    .serviceName("Service 2")
                    .timeRequired("2 hours")
                    .price(200.00f)
                    .build());
    }

    @Test
    public void whenGetAllServices_thenReturnAllServices() throws Exception {
        // Mock the service to return predefined data
        when(serviceService.getAllServices()).thenReturn(serviceResponseModels);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())  // Assert the status is 200 OK
                .andExpect(jsonPath("$.size()").value(2));  // Assert there are 2 services in the response
    }
}
