package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.customerssubdomain.businesslayer.CustomerService;
import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    private List<Customer> customers;
    private List<CustomerResponseModel> customerResponseModels;

    @BeforeEach
    public void setup() {
        Address address = new Address("123 Street", "City", "Postal Code", "province", "Country");

        customers = Arrays.asList(
                new Customer(new CustomerIdentifier(), "John", "Doe", "email@email.com", address),
                new Customer(new CustomerIdentifier(), "John2", "Doe2", "email@email.com2", address)
        );

        customerResponseModels = Arrays.asList(
                CustomerResponseModel.builder()
                        .customerId(customers.get(0).getCustomerIdentifier().getCustomerId())
                        .customerFirstName("John")
                        .customerLastName("Doe")
                        .customerEmailAddress("email@email.com")
                        .city("city")
                        .country("country")
                        .postalCode("postalCode")
                        .province("province")
                        .streetAddress("street")
                        .build(),
                CustomerResponseModel.builder()
                        .customerId("2")
                        .customerFirstName("John2")
                        .customerLastName("Doe2")
                        .customerEmailAddress("email@email.com2")
                        .city("city2")
                        .country("country2")
                        .postalCode("postalCode2")
                        .province("province2")
                        .streetAddress("street2")
                        .build()
        );
    }

    @Test
    void whenGetCustomerByCustomerId_thenReturnCustomer() throws Exception {
        // Arrange: Mock the service response
        String customerId = customers.get(0).getCustomerIdentifier().getCustomerId();

        when(customerService.getCustomerByCustomerId(customerId)).thenReturn(customerResponseModels.get(0));

        // Act & Assert: Perform the HTTP GET request
        mockMvc.perform(get("/api/customers/{customerId}", customerId)) // Pass the customerId here
                .andExpect(status().isOk()) // Check for HTTP 200 OK
                .andExpect(jsonPath("$.customerId").value(customerResponseModels.get(0).getCustomerId())) // Assert specific fields
                .andExpect(jsonPath("$.customerFirstName").value(customerResponseModels.get(0).getCustomerFirstName()))
                .andExpect(jsonPath("$.customerLastName").value(customerResponseModels.get(0).getCustomerLastName()))
                .andExpect(jsonPath("$.customerEmailAddress").value(customerResponseModels.get(0).getCustomerEmailAddress()))
                .andExpect(jsonPath("$.city").value(customerResponseModels.get(0).getCity()))
                .andExpect(jsonPath("$.country").value(customerResponseModels.get(0).getCountry()));
    }

}