package com.example.highenddetailing.customerssubdomain.businesslayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerRequestMapper;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerResponseMapper;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerResponseMapper customerResponseMapper;

    @Mock
    private CustomerRequestMapper customerRequestMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetCustomers_ShouldReturnEmptyList() {
        // Arrange
        List<Customer> emptyList = Collections.emptyList();
        when(customerRepository.findAll()).thenReturn(emptyList);

        // Act
        List<CustomerResponseModel> responseModels = customerService.getCustomers();

        // Assert
        assertEquals(0, responseModels.size());
        verify(customerRepository).findAll();
    }

    @Test
    void testUpdateCustomer_ShouldUpdateAndReturnResponseModel() {
        // Arrange
        String customerId = "CUST-123";
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1);
        existingCustomer.setCustomerIdentifier(new CustomerIdentifier());

        CustomerRequestModel requestModel = new CustomerRequestModel("John", "Doe", "johndoe@example.com",
                "123 Main St", "Anytown", "A1B2C3", "Ontario", "Canada","auth0-1234");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setCustomerIdentifier(new CustomerIdentifier());
        updatedCustomer.setCustomerFirstName(requestModel.getCustomerFirstName());
        updatedCustomer.setCustomerLastName(requestModel.getCustomerLastName());
        updatedCustomer.setCustomerEmailAddress(requestModel.getCustomerEmailAddress());
        updatedCustomer.setAddress(new Address(
                requestModel.getStreetAddress(),
                requestModel.getCity(),
                requestModel.getPostalCode(),
                requestModel.getProvince(),
                requestModel.getCountry()
        ));

        CustomerResponseModel expectedResponse = new CustomerResponseModel();
        expectedResponse.setCustomerId(customerId);
        expectedResponse.setCustomerFirstName(requestModel.getCustomerFirstName());
        expectedResponse.setCustomerLastName(requestModel.getCustomerLastName());
        expectedResponse.setCustomerEmailAddress(requestModel.getCustomerEmailAddress());
        // ... (set other expected response fields)

        when(customerRepository.findByCustomerIdentifier_CustomerId(customerId)).thenReturn(existingCustomer);
        when(customerRequestMapper.requestModelToEntity(any(CustomerRequestModel.class), any(CustomerIdentifier.class), any(Address.class))).thenReturn(updatedCustomer);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        when(customerResponseMapper.entityToResponseModel(updatedCustomer)).thenReturn(expectedResponse);

        // Act
        CustomerResponseModel response = customerService.updateCustomer(requestModel, customerId);

        // Assert
        assertEquals(expectedResponse, response);
        verify(customerRepository).findByCustomerIdentifier_CustomerId(customerId);
        verify(customerRequestMapper).requestModelToEntity(eq(requestModel), eq(existingCustomer.getCustomerIdentifier()), any(Address.class));
        verify(customerRepository).save(updatedCustomer);
        verify(customerResponseMapper).entityToResponseModel(updatedCustomer);
    }

    @Test
    void testDeleteCustomer_ShouldDeleteCustomer() {
        String customerId = "CUST-123";
        Customer customer = new Customer();
        customer.setId(1);
        customer.setCustomerIdentifier(new CustomerIdentifier());

        when(customerRepository.findByCustomerIdentifier_CustomerId(customerId)).thenReturn(customer);

        customerService.deleteCustomer(customerId);

        verify(customerRepository).delete(customer);
    }
    @Test
    void testGetCustomerById_ShouldReturnCustomerResponseModel() {
        // Arrange
        String customerId = "CUST-123";
        Customer customer = new Customer();
        customer.setId(1);
        customer.setCustomerIdentifier(new CustomerIdentifier(customerId));
        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmailAddress("johndoe@example.com");
        customer.setAddress(new Address("123 Main St", "Anytown", "12345", "Ontario", "Canada"));

        CustomerResponseModel expectedResponse = new CustomerResponseModel();
        expectedResponse.setCustomerId(customerId);
        expectedResponse.setCustomerFirstName("John");
        expectedResponse.setCustomerLastName("Doe");
        expectedResponse.setCustomerEmailAddress("johndoe@example.com");
        expectedResponse.setStreetAddress("123 Main St");
        expectedResponse.setCity("Anytown");
        expectedResponse.setPostalCode("12345");
        expectedResponse.setProvince("Ontario");
        expectedResponse.setCountry("Canada");

        when(customerRepository.findByCustomerIdentifier_CustomerId(customerId)).thenReturn(customer);
        when(customerResponseMapper.entityToResponseModel(customer)).thenReturn(expectedResponse);

        // Act
        CustomerResponseModel actualResponse = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(customerRepository).findByCustomerIdentifier_CustomerId(customerId);
        verify(customerResponseMapper).entityToResponseModel(customer);
    }
}
