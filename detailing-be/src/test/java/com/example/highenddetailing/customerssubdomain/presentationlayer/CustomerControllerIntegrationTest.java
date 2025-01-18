package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;  // This will hold the random port assigned to the embedded server

    @Autowired
    private CustomerRepository customerRepository;  // The customer repository for database interactions

    private RestTemplate restTemplate;  // RestTemplate for making HTTP requests

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();  // Initialize RestTemplate
    }

    @BeforeEach
    public void initData() {
        // Insert mock data into the database (if needed)
        customerRepository.saveAll(Arrays.asList(
                new Customer(new CustomerIdentifier(), "John", "Doe", "johndoe@example.com", new Address("street1", "city1", "postalcode1", "province1", "country1")),
                new Customer(new CustomerIdentifier(), "Alice", "Sza", "alicesza@example.com", new Address("street2", "city2", "postalcode2", "province2", "country2")),
                new Customer(new CustomerIdentifier(), "John", "Dae", "johndae@example.com", new Address("street3", "city3", "postalcode3", "province3", "country3")),
                new Customer(new CustomerIdentifier(), "Johny", "Doee", "johnydoee@example.com", new Address("street4", "city4", "postalcode4", "province4", "country4")),
                new Customer(new CustomerIdentifier(), "Johnas", "Doeon", "johnasdoeon@example.com", new Address("street5", "city5", "postalcode5", "province5", "country5"))
        ));
    }

//    @Test
//    public void whenGetAllCustomers_thenReturnAllCustomers() {
//        // Construct the URL for the customers
//        String url = "http://localhost:" + port + "/api/customers";  // Use the random port assigned to the app
//
//        // Make a GET request to the API
//        ResponseEntity<List> response = restTemplate.exchange(
//                url,
//                org.springframework.http.HttpMethod.GET,
//                null,
//                List.class
//        );
//
//        // Assert that the response is OK and contains 5 customers
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(5, response.getBody().size());
//    }





}

