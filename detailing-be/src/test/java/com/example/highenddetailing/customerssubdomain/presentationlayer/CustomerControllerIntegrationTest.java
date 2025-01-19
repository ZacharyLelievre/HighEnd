package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.authsubdomain.SecurityConfig;
import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // Initialize RestTemplate
        restTemplate = new RestTemplate();

        // Clear the database to ensure a clean state
        customerRepository.deleteAll();
        customerRepository.flush(); // Ensure deletion is executed immediately

        // Insert mock data into the database
        customerRepository.saveAll(Arrays.asList(
                new Customer(new CustomerIdentifier("id1"), "John", "Doe", "johndoe@example.com",
                        new Address("street1", "city1", "postalcode1", "province1", "country1")),
                new Customer(new CustomerIdentifier("id2"), "Alice", "Sza", "alicesza@example.com",
                        new Address("street2", "city2", "postalcode2", "province2", "country2")),
                new Customer(new CustomerIdentifier("id3"), "John", "Dae", "johndae@example.com",
                        new Address("street3", "city3", "postalcode3", "province3", "country3")),
                new Customer(new CustomerIdentifier("id4"), "Johny", "Doee", "johnydoee@example.com",
                        new Address("street4", "city4", "postalcode4", "province4", "country4")),
                new Customer(new CustomerIdentifier("id5"), "Johnas", "Doeon", "johnasdoeon@example.com",
                        new Address("street5", "city5", "postalcode5", "province5", "country5"))
        ));
        customerRepository.flush(); // Ensure data is persisted immediately
    }

    @Test
    public void whenGetAllCustomers_thenReturnAllCustomers() {
        // Construct the URL for the customers API
        String url = "http://localhost:" + port + "/api/customers";

        // Make a GET request to the API
        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                List.class
        );

        // Assert that the response is OK and contains 5 customers
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(5, response.getBody().size(), "There should be 5 customers in the response");
    }
}