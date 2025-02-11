//package com.example.highenddetailing.customerssubdomain.presentationlayer;
//
//import com.example.highenddetailing.authsubdomain.SecurityConfig;
//import com.example.highenddetailing.customerssubdomain.datalayer.Address;
//import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
//import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
//import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@Import(SecurityConfig.class)
//public class CustomerControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    private RestTemplate restTemplate;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize RestTemplate
//        restTemplate = new RestTemplate();
//
//        // Clear the database to ensure a clean state
//        customerRepository.deleteAll();
//        customerRepository.flush(); // Ensure deletion is executed immediately
//
//        // Insert mock data into the database
//        customerRepository.saveAll(Arrays.asList(
//                new Customer(new CustomerIdentifier("id1"), "John", "Doe", "johndoe@example.com",
//                        new Address("street1", "city1", "postalcode1", "province1", "country1")),
//                new Customer(new CustomerIdentifier("id2"), "Alice", "Sza", "alicesza@example.com",
//                        new Address("street2", "city2", "postalcode2", "province2", "country2")),
//                new Customer(new CustomerIdentifier("id3"), "John", "Dae", "johndae@example.com",
//                        new Address("street3", "city3", "postalcode3", "province3", "country3")),
//                new Customer(new CustomerIdentifier("id4"), "Johny", "Doee", "johnydoee@example.com",
//                        new Address("street4", "city4", "postalcode4", "province4", "country4")),
//                new Customer(new CustomerIdentifier("id5"), "Johnas", "Doeon", "johnasdoeon@example.com",
//                        new Address("street5", "city5", "postalcode5", "province5", "country5"))
//        ));
//        customerRepository.flush(); // Ensure data is persisted immediately
//    }
//
//    @Test
//    public void whenGetAllCustomers_thenReturnAllCustomers() {
//        // Construct the URL for the customers API
//        String url = "http://localhost:" + port + "/api/customers";
//
//        // Make a GET request to the API
//        ResponseEntity<List> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                List.class
//        );
//
//        // Assert that the response is OK and contains 5 customers
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
//        assertNotNull(response.getBody(), "Response body should not be null");
//        assertEquals(5, response.getBody().size(), "There should be 5 customers in the response");
//    }
//    @Test
//    public void whenCreateCustomerWithValidRequest_thenReturnCreatedCustomer() {
//        // Construct the URL for the customers API
//        String url = "http://localhost:" + port + "/api/customers";
//
//        // Create a valid CustomerRequestModel
//        CustomerRequestModel requestModel = new CustomerRequestModel(
//                "Jane",              // First Name
//                "Doe",               // Last Name
//                "jane.doe@example.com", // Email
//                "123 Main St",       // Street Address
//                "Anytown",           // City
//                "12345",             // Postal Code
//                "Province",          // Province
//                "Country",           // Country
//                "auth0-id-jane"      // Auth0 Sub
//        );
//
//        // Set headers for the request
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Create an HttpEntity with the request body and headers
//        HttpEntity<CustomerRequestModel> request = new HttpEntity<>(requestModel, headers);
//
//        // Make a POST request to the API
//        ResponseEntity<CustomerResponseModel> response = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                request,
//                CustomerResponseModel.class
//        );
//
//        // Assert the response
//        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response status should be CREATED");
//        assertNotNull(response.getBody(), "Response body should not be null");
//
//        // Assert the response body
//        CustomerResponseModel responseBody = response.getBody();
//        assertEquals("Jane", responseBody.getCustomerFirstName(), "First name should match");
//        assertEquals("Doe", responseBody.getCustomerLastName(), "Last name should match");
//        assertEquals("jane.doe@example.com", responseBody.getCustomerEmailAddress(), "Email should match");
//    }
//    @Test
//    public void whenCreateCustomerWithInvalidRequest_thenReturnBadRequest() {
//
//        String url = "http://localhost:" + port + "/api/customers";
//
//
//        CustomerRequestModel requestModel = new CustomerRequestModel(
//                "Jane",
//                "Doe",
//                "jane.doe@example.com",
//                "123 Main St",
//                "Anytown",
//                "12345",
//                "Province",
//                "Country",
//                null
//        );
//
//        // Set headers for the request
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Create an HttpEntity with the request body and headers
//        HttpEntity<CustomerRequestModel> request = new HttpEntity<>(requestModel, headers);
//
//        try {
//            // Make a POST request to the API
//            restTemplate.exchange(
//                    url,
//                    HttpMethod.POST,
//                    request,
//                    Void.class
//            );
//        } catch (org.springframework.web.client.HttpClientErrorException.BadRequest ex) {
//            // Assert that the exception contains a 400 status code
//            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode(), "Response status should be BAD_REQUEST");
//        }
//    }
//
//    @Test
//    public void whenUpdateCustomerWithValidRequest_thenReturnUpdatedCustomer() {
//        // Prepare the customer ID you want to update (use an existing ID from the mock data)
//        String customerId = "id1";
//
//        // Construct the URL for the PUT request
//        String url = "http://localhost:" + port + "/api/customers/" + customerId;
//
//        // Create a valid CustomerRequestModel to update the customer
//        CustomerRequestModel requestModel = new CustomerRequestModel(
//                "UpdatedName",              // Updated First Name
//                "UpdatedLastName",          // Updated Last Name
//                "updated.email@example.com", // Updated Email
//                "456 New St",               // Updated Street Address
//                "NewCity",                  // Updated City
//                "67890",                    // Updated Postal Code
//                "NewProvince",              // Updated Province
//                "NewCountry",               // Updated Country
//                "auth0-id-updated"          // Updated Auth0 Sub
//        );
//
//        // Set headers for the request
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Create an HttpEntity with the request body and headers
//        HttpEntity<CustomerRequestModel> request = new HttpEntity<>(requestModel, headers);
//
//        // Make a PUT request to the API
//        ResponseEntity<CustomerResponseModel> response = restTemplate.exchange(
//                url,
//                HttpMethod.PUT,
//                request,
//                CustomerResponseModel.class
//        );
//
//        // Assert the response
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
//        assertNotNull(response.getBody(), "Response body should not be null");
//
//        // Assert the response body
//        CustomerResponseModel responseBody = response.getBody();
//        assertEquals("UpdatedName", responseBody.getCustomerFirstName(), "First name should be updated");
//        assertEquals("UpdatedLastName", responseBody.getCustomerLastName(), "Last name should be updated");
//        assertEquals("updated.email@example.com", responseBody.getCustomerEmailAddress(), "Email should be updated");
//    }
//
//    @Test
//    public void whenGetCustomerByIdWithValidId_thenReturnCustomer() {
//        // Prepare the valid customer ID (use an existing ID from the mock data)
//        String customerId = "id1";
//
//        // Construct the URL for the GET request
//        String url = "http://localhost:" + port + "/api/customers/" + customerId;
//
//        // Make a GET request to the API
//        ResponseEntity<CustomerResponseModel> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                CustomerResponseModel.class
//        );
//
//        // Assert the response
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
//        assertNotNull(response.getBody(), "Response body should not be null");
//
//        // Assert the response body
//        CustomerResponseModel responseBody = response.getBody();
//        assertEquals("John", responseBody.getCustomerFirstName(), "First name should match");
//        assertEquals("Doe", responseBody.getCustomerLastName(), "Last name should match");
//        assertEquals("johndoe@example.com", responseBody.getCustomerEmailAddress(), "Email should match");
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}