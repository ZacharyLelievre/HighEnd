package com.example.highenddetailing.customerssubdomain.presentationlayer;


import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerResponseModelTest {

    @Test
    void testCustomerResponseModelBuilder() {
        // Arrange
        int id = 1;
        CustomerIdentifier customerIdentifier = new CustomerIdentifier();
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmailAddress = "john.doe@example.com";
        Address address = new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland");

        // Act
        Customer responseModel = Customer.builder()
                .id(id)
                .customerIdentifier(customerIdentifier)
                .customerFirstName(customerFirstName)
                .customerLastName(customerLastName)
                .customerEmailAddress(customerEmailAddress)
                .address(address)
                .build();

        // Assert
        assertEquals(responseModel.getId(), id);
        assertEquals(responseModel.getCustomerIdentifier(), customerIdentifier);
        assertEquals(responseModel.getCustomerFirstName(), customerFirstName);
        assertEquals(responseModel.getCustomerLastName(), customerLastName);
        assertEquals(responseModel.getCustomerEmailAddress(), customerEmailAddress);
        assertEquals(responseModel.getAddress(), address);
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Customer responseModel = new Customer();

        // Assert
        assertThat(responseModel.getId()).isNull();
        assertThat(responseModel.getCustomerIdentifier()).isNull();
        assertThat(responseModel.getCustomerFirstName()).isNull();
        assertThat(responseModel.getCustomerLastName()).isNull();
        assertThat(responseModel.getCustomerEmailAddress()).isNull();
        assertThat(responseModel.getAddress()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        int id = 1;
        CustomerIdentifier customerIdentifier = new CustomerIdentifier();
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmailAddress = "john.doe@example.com";
        Address address = new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland");

        // Act
        Customer responseModel = new Customer(id, customerIdentifier, customerFirstName, customerLastName, customerEmailAddress, address);

        // Assert
        assertEquals(responseModel.getId(), id);
        assertEquals(responseModel.getCustomerIdentifier(), customerIdentifier);
        assertEquals(responseModel.getCustomerFirstName(), customerFirstName);
        assertEquals(responseModel.getCustomerLastName(), customerLastName);
        assertEquals(responseModel.getCustomerEmailAddress(), customerEmailAddress);
        assertEquals(responseModel.getAddress(), address);
    }

    @Test
    void testSetters() {
        // Arrange
        Customer customer = new Customer();

        // Act
        customer.setId(1);
        customer.setCustomerIdentifier(new CustomerIdentifier());
        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmailAddress("john.doe@example.com");
        customer.setAddress(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        // Assert
        assertThat(customer.getCustomerFirstName()).isEqualTo("John");
        assertThat(customer.getCustomerLastName()).isEqualTo("Doe");
        assertThat(customer.getCustomerEmailAddress()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Customer customer1 = new Customer(1, new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));
        Customer customer2 = new Customer(1, customer1.getCustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));
        Customer customer3 = new Customer(2, new CustomerIdentifier(), "Jane", "Smith", "jane.smith@example.com", new Address("456 Another St", "Townsville", "Stateville", "54321", "Countryland"));

        // Act & Assert
        assertThat(customer1).isEqualTo(customer2);
        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
        assertThat(customer1).isNotEqualTo(customer3);
        assertThat(customer1.hashCode()).isNotEqualTo(customer3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Customer.CustomerBuilder builder = Customer.builder();
        builder.id(1)
                .customerIdentifier(new CustomerIdentifier())
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("john.doe@example.com")
                .address(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "customerFirstName=John",
                "customerLastName=Doe",
                "customerEmailAddress=john.doe@example.com"
        );
    }

    @Test
    void testCanEqual() {
        Customer customer1 = new Customer(1, new CustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));
        Customer customer2 = new Customer(1, customer1.getCustomerIdentifier(), "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        assertThat(customer1.equals(customer2)).isTrue();
        assertThat(customer1.equals(new Object())).isFalse();
    }

    @Test
    void testBuild() {
        // Arrange
        Customer.CustomerBuilder builder = Customer.builder();
        builder.id(1)
                .customerIdentifier(new CustomerIdentifier())
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("john.doe@example.com")
                .address(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));

        // Act
        Customer customer = builder.build();

        // Assert
        assertThat(customer.getId()).isEqualTo(1);
        assertThat(customer.getCustomerFirstName()).isEqualTo("John");
        assertThat(customer.getCustomerLastName()).isEqualTo("Doe");
        assertThat(customer.getCustomerEmailAddress()).isEqualTo("john.doe@example.com");
        assertThat(customer.getAddress()).isEqualTo(new Address("123 Main St", "Cityville", "Stateville", "12345", "Countryland"));
    }
}