package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;  // This will hold the random port assigned to the embedded server

    @Autowired
    private CustomerRepository customerRepository;  // The service repository for database interactions

    private RestTemplate restTemplate;  // RestTemplate for making HTTP requests

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();  // Initialize RestTemplate
    }

    @BeforeEach
    public void cleanDatabase() {
        customerRepository.deleteAll(); // Ensure a clean database before each test
    }
    @BeforeEach
    public void initData() {
        // Insert mock data into the database (if needed)
        customerRepository.deleteAll(); // Clear database to isolate tests
        customerRepository.saveAll(Arrays.asList(
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA")),
                new Customer(new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "New York", "10001", "NY", "USA"))
        ));
    }

    @Test
    public void whenGetCustomerById_thenReturnCustomer() {
        // Arrange: Save a test customer to the repository
        Customer customer = customerRepository.save(
                new Customer(
                        new CustomerIdentifier(),
                        "Jane",
                        "Doe",
                        "jane.doe@example.com",
                        new Address("456 Elm St", "Los Angeles", "90001", "CA", "USA")
                )
        );

        String url = "http://localhost:" + port + "/api/customers/" + customer.getCustomerIdentifier().getCustomerId();

        // Act: Make a GET request for the specific customer
        ResponseEntity<CustomerResponseModel> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                CustomerResponseModel.class
        );

        // Assert: Verify the customer details
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        CustomerResponseModel customerResponse = response.getBody();
        assertEquals(customer.getCustomerIdentifier().getCustomerId(), customerResponse.getCustomerId());
        assertEquals(customer.getCustomerFirstName(), customerResponse.getCustomerFirstName());
        assertEquals(customer.getCustomerLastName(), customerResponse.getCustomerLastName());
        assertEquals(customer.getCustomerEmailAddress(), customerResponse.getCustomerEmailAddress());
        assertEquals(customer.getAddress().getStreetAddress(), customerResponse.getStreetAddress());
        assertEquals(customer.getAddress().getCity(), customerResponse.getCity());
        assertEquals(customer.getAddress().getProvince(), customerResponse.getProvince());
        assertEquals(customer.getAddress().getPostalCode(), customerResponse.getPostalCode());
        assertEquals(customer.getAddress().getCountry(), customerResponse.getCountry());
    }

}
