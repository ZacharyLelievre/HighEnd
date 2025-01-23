package com.example.highenddetailing.customerssubdomain.datalayer;

import com.example.highenddetailing.authsubdomain.SecurityConfig;
import com.example.highenddetailing.customerssubdomain.businesslayer.CustomerServiceImpl;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerRequestMapper;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerResponseMapper;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerResponseMapper customerResponseMapper;

    @MockBean
    private CustomerRequestMapper customerRequestMapper;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    void whenFindAll_thenReturnAllCustomers() {
        // Arrange: Prepare sample customers
        Customer customer1 = new Customer();
        customer1.setCustomerIdentifier(new CustomerIdentifier());
        customer1.setCustomerFirstName("John");
        customer1.setCustomerLastName("Doe");
        customer1.setCustomerEmailAddress("johndoe@example.com");
        customer1.setAddress(new Address("123 Main St", "Anytown", "12345", "CA", "USA"));

        Customer customer2 = new Customer();
        customer2.setCustomerIdentifier(new CustomerIdentifier());
        customer2.setCustomerFirstName("Jane");
        customer2.setCustomerLastName("Doe");
        customer2.setCustomerEmailAddress("janedoe@example.com");
        customer2.setAddress(new Address("456 Elm St", "Anytown", "54321", "CA", "USA"));

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Act: Fetch all customers
        List<Customer> customers = customerRepository.findAll();

        // Assert: Verify the results
        assertNotNull(customers);
        assertEquals(2, customers.size());

        // Verify the first customer
        assertEquals(customer1.getCustomerIdentifier().getCustomerId(), customers.get(0).getCustomerIdentifier().getCustomerId());
        assertEquals("John", customers.get(0).getCustomerFirstName());
        assertEquals("Doe", customers.get(0).getCustomerLastName());
        assertEquals("johndoe@example.com", customers.get(0).getCustomerEmailAddress());
        assertEquals("123 Main St", customers.get(0).getAddress().getStreetAddress());
        assertEquals("Anytown", customers.get(0).getAddress().getCity());
        assertEquals("12345", customers.get(0).getAddress().getPostalCode());
        assertEquals("CA", customers.get(0).getAddress().getProvince());
        assertEquals("USA", customers.get(0).getAddress().getCountry());

        // Verify the second customer
        assertEquals(customer2.getCustomerIdentifier().getCustomerId(), customers.get(1).getCustomerIdentifier().getCustomerId());
        assertEquals("Jane", customers.get(1).getCustomerFirstName());
        assertEquals("Doe", customers.get(1).getCustomerLastName());
        assertEquals("janedoe@example.com", customers.get(1).getCustomerEmailAddress());
        assertEquals("456 Elm St", customers.get(1).getAddress().getStreetAddress());
        assertEquals("Anytown", customers.get(1).getAddress().getCity());
        assertEquals("54321", customers.get(1).getAddress().getPostalCode());
        assertEquals("CA", customers.get(1).getAddress().getProvince());
        assertEquals("USA", customers.get(1).getAddress().getCountry());
    }

    @Test
    public void testDeleteCustomer_shouldDeleteCustomerById() {
        // Arrange
        Address address = new Address("street1", "city1", "postalCode", "province1", "country1");
        Customer customer = Customer.builder()
                .id(1)
                .customerIdentifier(new CustomerIdentifier())
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("johndoe@example.com")
                .address(address)
                .build();
        customerRepository.save(customer);
        String customerId = customer.getCustomerIdentifier().getCustomerId();

        // Act
        new CustomerServiceImpl(customerRepository, customerResponseMapper, customerRequestMapper).deleteCustomer(customerId);

        // Assert
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assertFalse(deletedCustomer.isPresent());
    }


   private Customer createCustomer(){
        return Customer.builder()
                .id(1)
                .customerIdentifier(new CustomerIdentifier())
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("johndoe@example.com")
                .address(new Address("street1", "city1", "postalCode", "province1", "country1"))
                .build();

   }
    @Test
    void testFindByCustomerId_ShouldReturnCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerIdentifier(new CustomerIdentifier("CUST-123"));
        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmailAddress("johndoe@example.com");
        customer.setAddress(new Address("123 Main St", "Anytown", "12345", "Ontario", "Canada"));

        customerRepository.save(customer);

        // Act
        Customer fetchedCustomer = customerRepository.findByCustomerIdentifier_CustomerId("CUST-123");

        // Assert
        assertNotNull(fetchedCustomer);
        assertEquals("CUST-123", fetchedCustomer.getCustomerIdentifier().getCustomerId());
        assertEquals("John", fetchedCustomer.getCustomerFirstName());
        assertEquals("Doe", fetchedCustomer.getCustomerLastName());
        assertEquals("johndoe@example.com", fetchedCustomer.getCustomerEmailAddress());
        assertEquals("123 Main St", fetchedCustomer.getAddress().getStreetAddress());
        assertEquals("Anytown", fetchedCustomer.getAddress().getCity());
        assertEquals("12345", fetchedCustomer.getAddress().getPostalCode());
        assertEquals("Ontario", fetchedCustomer.getAddress().getProvince());
        assertEquals("Canada", fetchedCustomer.getAddress().getCountry());
    }

}
