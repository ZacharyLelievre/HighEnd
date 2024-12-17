package com.example.highenddetailing.customerssubdomain.presentationlayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRequestModelTest {

    @Test
    void testCustomerRequestModelBuilder() {
        // Arrange
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmailAddress = "john.doe@example.com";
        String streetAddress = "123 Main St";
        String city = "Cityville";
        String postalCode = "12345";
        String province = "Stateville";
        String country = "Countryland";

        // Act
        CustomerRequestModel requestModel = CustomerRequestModel.builder()
                .customerFirstName(customerFirstName)
                .customerLastName(customerLastName)
                .customerEmailAddress(customerEmailAddress)
                .streetAddress(streetAddress)
                .city(city)
                .postalCode(postalCode)
                .province(province)
                .country(country)
                .build();

        // Assert
        assertEquals(requestModel.getCustomerFirstName(), customerFirstName);
        assertEquals(requestModel.getCustomerLastName(), customerLastName);
        assertEquals(requestModel.getCustomerEmailAddress(), customerEmailAddress);
        assertEquals(requestModel.getStreetAddress(), streetAddress);
        assertEquals(requestModel.getCity(), city);
        assertEquals(requestModel.getPostalCode(), postalCode);
        assertEquals(requestModel.getProvince(), province);
        assertEquals(requestModel.getCountry(), country);
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        CustomerRequestModel requestModel = new CustomerRequestModel();

        // Assert
        assertThat(requestModel.getCustomerFirstName()).isNull();
        assertThat(requestModel.getCustomerLastName()).isNull();
        assertThat(requestModel.getCustomerEmailAddress()).isNull();
        assertThat(requestModel.getStreetAddress()).isNull();
        assertThat(requestModel.getCity()).isNull();
        assertThat(requestModel.getPostalCode()).isNull();
        assertThat(requestModel.getProvince()).isNull();
        assertThat(requestModel.getCountry()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmailAddress = "john.doe@example.com";
        String streetAddress = "123 Main St";
        String city = "Cityville";
        String postalCode = "12345";
        String province = "Stateville";
        String country = "Countryland";

        // Act
        CustomerRequestModel requestModel = new CustomerRequestModel(customerFirstName, customerLastName, customerEmailAddress, streetAddress, city, postalCode, province, country);

        // Assert
        assertEquals(requestModel.getCustomerFirstName(), customerFirstName);
        assertEquals(requestModel.getCustomerLastName(), customerLastName);
        assertEquals(requestModel.getCustomerEmailAddress(), customerEmailAddress);
        assertEquals(requestModel.getStreetAddress(), streetAddress);
        assertEquals(requestModel.getCity(), city);
        assertEquals(requestModel.getPostalCode(), postalCode);
        assertEquals(requestModel.getProvince(), province);
        assertEquals(requestModel.getCountry(), country);
    }

    @Test
    void testSetters() {
        // Arrange
        CustomerRequestModel requestModel = new CustomerRequestModel();

        // Act
        requestModel.setCustomerFirstName("John");
        requestModel.setCustomerLastName("Doe");
        requestModel.setCustomerEmailAddress("john.doe@example.com");
        requestModel.setStreetAddress("123 Main St");
        requestModel.setCity("Cityville");
        requestModel.setPostalCode("12345");
        requestModel.setProvince("Stateville");
        requestModel.setCountry("Countryland");

        // Assert
        assertThat(requestModel.getCustomerFirstName()).isEqualTo("John");
        assertThat(requestModel.getCustomerLastName()).isEqualTo("Doe");
        assertThat(requestModel.getCustomerEmailAddress()).isEqualTo("john.doe@example.com");
        assertThat(requestModel.getStreetAddress()).isEqualTo("123 Main St");
        assertThat(requestModel.getCity()).isEqualTo("Cityville");
        assertThat(requestModel.getPostalCode()).isEqualTo("12345");
        assertThat(requestModel.getProvince()).isEqualTo("Stateville");
        assertThat(requestModel.getCountry()).isEqualTo("Countryland");
    }

    @Test
    void testGetters() {
        // Arrange
        CustomerRequestModel requestModel = new CustomerRequestModel();
        requestModel.setCustomerFirstName("John");
        requestModel.setCustomerLastName("Doe");
        requestModel.setCustomerEmailAddress("john.doe@example.com");
        requestModel.setStreetAddress("123 Main St");
        requestModel.setCity("Cityville");
        requestModel.setPostalCode("12345");
        requestModel.setProvince("Stateville");
        requestModel.setCountry("Countryland");

        // Act & Assert
        assertEquals("John", requestModel.getCustomerFirstName());
        assertEquals("Doe", requestModel.getCustomerLastName());
        assertEquals("john.doe@example.com", requestModel.getCustomerEmailAddress());
        assertEquals("123 Main St", requestModel.getStreetAddress());
        assertEquals("Cityville", requestModel.getCity());
        assertEquals("12345", requestModel.getPostalCode());
        assertEquals("Stateville", requestModel.getProvince());
        assertEquals("Countryland", requestModel.getCountry());
    }

//    @Test
//    void testEqualsAndHashCode() {
//        // Arrange
//        CustomerRequestModel requestModel1 = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "123 Main St", "Cityville", "12345", "Stateville", "Countryland");
//        CustomerRequestModel requestModel2 = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "123 Main St", "Cityville", "12345", "Stateville", "Countryland");
//        CustomerRequestModel requestModel3 = new CustomerRequestModel("Jane", "Smith", "jane.smith@example.com", "456 Another St", "Townsville", "54321", "Stateville", "Countryland");
//
//        // Act & Assert
//        assertThat(requestModel1).isEqualTo(requestModel2);
//        assertThat(requestModel1.hashCode()).isEqualTo(requestModel2.hashCode());
//        assertThat(requestModel1).isNotEqualTo(requestModel3);
//        assertThat(requestModel1.hashCode()).isNotEqualTo(requestModel3.hashCode());
//    }

    @Test
    void testToString() {
        // Arrange
        CustomerRequestModel.CustomerRequestModelBuilder builder = CustomerRequestModel.builder();
        builder.customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("john.doe@example.com")
                .streetAddress("123 Main St")
                .city("Cityville")
                .postalCode("12345")
                .province("Stateville")
                .country("Countryland");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "customerFirstName=John",
                "customerLastName=Doe",
                "customerEmailAddress=john.doe@example.com",
                "streetAddress=123 Main St",
                "city=Cityville",
                "postalCode=12345",
                "province=Stateville",
                "country=Countryland"
        );
    }

    @Test
    void testCanEqual() {
        CustomerRequestModel requestModel1 = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "123 Main St", "Cityville", "12345", "Stateville", "Countryland");
        CustomerRequestModel requestModel2 = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "123 Main St", "Cityville", "12345", "Stateville", "Countryland");

        assertThat(requestModel1.equals(requestModel2)).isFalse();
        assertThat(requestModel1.equals(new Object())).isFalse();
    }

    @Test
    void testBuild() {
        // Arrange
        CustomerRequestModel.CustomerRequestModelBuilder builder = CustomerRequestModel.builder();
        builder.customerFirstName("John")
                .customerLastName("Doe")
                .customerEmailAddress("john.doe@example.com")
                .streetAddress("123 Main St")
                .city("Cityville")
                .postalCode("12345")
                .province("Stateville")
                .country("Countryland");

        // Act
        CustomerRequestModel requestModel = builder.build();

        // Assert
        assertThat(requestModel.getCustomerFirstName()).isEqualTo("John");
        assertThat(requestModel.getCustomerLastName()).isEqualTo("Doe");
        assertThat(requestModel.getCustomerEmailAddress()).isEqualTo("john.doe@example.com");
        assertThat(requestModel.getStreetAddress()).isEqualTo("123 Main St");
    }
}