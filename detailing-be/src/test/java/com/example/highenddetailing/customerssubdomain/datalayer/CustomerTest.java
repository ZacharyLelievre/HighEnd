package com.example.highenddetailing.customerssubdomain.datalayer;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    @Test
    void testEqualsAndHashCode() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setCustomerIdentifier(new CustomerIdentifier());
        customer1.setCustomerFirstName("John");
        customer1.setCustomerLastName("Doe");
        customer1.setCustomerEmailAddress("john.doe@example.com");
        customer1.setAddress(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        Customer customer2 = new Customer();
        customer2.setId(1);
        customer2.setCustomerIdentifier(customer1.getCustomerIdentifier()); // Reusing the same identifier for comparison
        customer2.setCustomerFirstName("John");
        customer2.setCustomerLastName("Doe");
        customer2.setCustomerEmailAddress("john.doe@example.com");
        customer2.setAddress(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        Customer customer = new Customer();
        customer.setId(1);
        CustomerIdentifier identifier = new CustomerIdentifier();
        customer.setCustomerIdentifier(identifier);
        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmailAddress("john.doe@example.com");
        customer.setAddress(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        assertEquals(1, customer.getId());
        assertEquals(identifier, customer.getCustomerIdentifier());
        assertEquals("John", customer.getCustomerFirstName());
        assertEquals("Doe", customer.getCustomerLastName());
        assertEquals("john.doe@example.com", customer.getCustomerEmailAddress());
        assertEquals(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"), customer.getAddress());
    }

    @Test
    void testCanEqual() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setCustomerFirstName("John");
        customer1.setCustomerLastName("Doe");
        customer1.setCustomerEmailAddress("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId(1);
        customer2.setCustomerFirstName("John");
        customer2.setCustomerLastName("Doe");
        customer2.setCustomerEmailAddress("john.doe@example.com");

        assertThat(customer1.equals(customer2)).isTrue();
        assertThat(customer1.equals(new Object())).isFalse();
    }

    @Test
    void testConstructorWithAllArgs() {
        CustomerIdentifier identifier = new CustomerIdentifier();
        Address address = new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland");
        Customer customer = new Customer(1, identifier, "John", "Doe", "john.doe@example.com", address);

        assertEquals(1, customer.getId());
        assertEquals(identifier, customer.getCustomerIdentifier());
        assertEquals("John", customer.getCustomerFirstName());
        assertEquals("Doe", customer.getCustomerLastName());
        assertEquals("john.doe@example.com", customer.getCustomerEmailAddress());
        assertEquals(address, customer.getAddress());
    }

    @Test
    void testCustomerBuilder() {
        Customer customer = Customer.builder()
                .id(1)
                .customerIdentifier(new CustomerIdentifier())
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("john.doe@example.com")
                .address(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"))
                .build();

        assertEquals(1, customer.getId());
        assertEquals("John", customer.getCustomerFirstName());
        assertEquals("Doe", customer.getCustomerLastName());
        assertEquals("john.doe@example.com", customer.getCustomerEmailAddress());
    }

    @Test
    void testEquals() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setCustomerFirstName("John");
        customer1.setCustomerLastName("Doe");
        customer1.setCustomerEmailAddress("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId(1);
        customer2.setCustomerFirstName("John");
        customer2.setCustomerLastName("Doe");
        customer2.setCustomerEmailAddress("john.doe@example.com");

        assertThat(customer1).isEqualTo(customer2);
    }

    @Test
    void testHashCode() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setCustomerFirstName("John");
        customer1.setCustomerLastName("Doe");
        customer1.setCustomerEmailAddress("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId(1);
        customer2.setCustomerFirstName("John");
        customer2.setCustomerLastName("Doe");
        customer2.setCustomerEmailAddress("john.doe@example.com");

        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Customer customer = new Customer();
        customer.setId(1);
        CustomerIdentifier identifier = new CustomerIdentifier();
        customer.setCustomerIdentifier(identifier);
        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmailAddress("john.doe@example.com");
        customer.setAddress(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        assertEquals(1, customer.getId());
        assertEquals("John", customer.getCustomerFirstName());
        assertEquals("Doe", customer.getCustomerLastName());
        assertEquals("john.doe@example.com", customer.getCustomerEmailAddress());
    }
}
