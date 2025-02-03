package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.authsubdomain.SecurityConfig;
import com.example.highenddetailing.customerssubdomain.businesslayer.CustomerService;
import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@Import(SecurityConfig.class)
@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc to simulate HTTP requests

    @MockBean
    private CustomerService customerService;  // Mock the CustomerService

    private List<Customer> customers;
    private List<CustomerResponseModel> customerResponseModels;

    @BeforeEach
    public void setup() {
        customers = Arrays.asList(
                new Customer(new CustomerIdentifier(), "John", "Doe", "johndoe@example.com", new Address("street1", "city1", "postalcode1", "province1", "country1")),
                new Customer(new CustomerIdentifier(), "Alice", "Sza", "alicesza@example.com", new Address("street2", "city2", "postalcode2", "province2", "country2")),
                new Customer(new CustomerIdentifier(), "John", "Dae", "johndae@example.com", new Address("street3", "city3", "postalcode3", "province3", "country3")),
                new Customer(new CustomerIdentifier(), "Johny", "Doee", "johnydoee@example.com", new Address("street4", "city4", "postalcode4", "province4", "country4")),
                new Customer(new CustomerIdentifier(), "Johnas", "Doeon", "johnasdoeon@example.com", new Address("street5", "city5", "postalcode5", "province5", "country5"))
        );


        customerResponseModels = Arrays.asList(
                CustomerResponseModel.builder()
                        .customerId("1")  // Example ID
                        .customerFirstName("John")
                        .customerLastName("Doe")
                        .customerEmailAddress("johndoe@example.com")
                        .streetAddress("123 Main St, Anytown, CA")
                        .city("Anytown")
                        .postalCode("123 ABG")
                        .province("QC")
                        .country("Canada")
                        .build(),
                CustomerResponseModel.builder()
                        .customerId("2")  // Example ID
                        .customerFirstName("Alice")
                        .customerLastName("Sza")
                        .customerEmailAddress("alicedxa@example.com")
                        .streetAddress("456 Main Town St")
                        .city("Anothertown")
                        .postalCode("456 GBA")
                        .province("QC")
                        .country("Canada")
                        .build()
        );
    }

    @Test
    public void whenGetAllCustomers_thenReturnAllCustomers() throws Exception {
        // Mock the service to return predefined data
        when(customerService.getCustomers()).thenReturn(customerResponseModels);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/customers"))  // Provide the URL in the get() method
                .andExpect(status().isOk())  // Assert the status is 200 OK
                .andExpect(jsonPath("$.size()").value(2))  // Assert there are 2 customers in the response
                .andExpect(jsonPath("$[0].customerFirstName").value("John"))  // Assert first customer's first name is "John"
                .andExpect(jsonPath("$[1].customerFirstName").value("Alice"));  // Assert second customer's first name is "Jane"
    }

    @Test
    public void whenDeleteCustomerWithValidId_thenReturnNoContent() throws Exception {
        // Mock the service to indicate successful deletion
        String customerId = "1"; // Using string customerId, as per your CustomerResponseModel

        // Mock the delete behavior
        doNothing().when(customerService).deleteCustomer(customerId);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/customers/{customerId}", customerId))
                .andExpect(status().isNoContent());  // Assert that the response status is 204 No Content

        // Verify that the delete method in the service was called with the correct ID
        verify(customerService, times(1)).deleteCustomer(customerId);
    }



}