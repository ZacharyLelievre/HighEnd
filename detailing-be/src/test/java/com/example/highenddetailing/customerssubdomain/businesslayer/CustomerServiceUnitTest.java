package com.example.highenddetailing.customerssubdomain.businesslayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerResponseMapper;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerResponseMapper customerResponseMapper;
    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void whenGetCustomerByCustomerId_thenReturnCustomer() {
        // Create test data
        Address address = new Address("123 Street", "City", "Postal Code", "province", "Country");
        Customer customer1 = new Customer(new CustomerIdentifier(), "John", "Doe", "email@email.com", address);

        CustomerResponseModel customerResponseModel = new CustomerResponseModel(
                customer1.getCustomerIdentifier().toString(),
                customer1.getCustomerFirstName(),
                customer1.getCustomerLastName(),
                customer1.getCustomerEmailAddress(),
                customer1.getAddress().getStreetAddress(),
                customer1.getAddress().getCity(),
                customer1.getAddress().getPostalCode(),
                customer1.getAddress().getProvince(),
                customer1.getAddress().getCountry()
        );

        // Mock repository and mapper behavior
        when(customerRepository.findCustomerByCustomerIdentifier_CustomerId(customer1.getCustomerIdentifier().getCustomerId()))
                .thenReturn(customer1);
        when(customerResponseMapper.entityToResponseModel(customer1)).thenReturn(customerResponseModel);

        // Call the service method
        CustomerResponseModel customerResponse = customerService.getCustomerByCustomerId(customer1.getCustomerIdentifier().getCustomerId());

        // Assert results
        assertNotNull(customerResponse);
        assertEquals(customer1.getCustomerIdentifier().toString(), customerResponse.getCustomerId());
        assertEquals(customer1.getCustomerFirstName(), customerResponse.getCustomerFirstName());
        assertEquals(customer1.getCustomerLastName(), customerResponse.getCustomerLastName());
        assertEquals(customer1.getCustomerEmailAddress(), customerResponse.getCustomerEmailAddress());
        assertEquals(customer1.getAddress().getCountry(), customerResponse.getCountry());
        assertEquals(customer1.getAddress().getCity(), customerResponse.getCity());
        assertEquals(customer1.getAddress().getProvince(), customerResponse.getProvince());
        assertEquals(customer1.getAddress().getPostalCode(), customerResponse.getPostalCode());
    }
}