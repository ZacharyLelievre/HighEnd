package com.example.highenddetailing.servicessubdomain.datalayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ServiceIdentifier identifier = new ServiceIdentifier();
        Service service1 = new Service(1, identifier, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service2 = new Service(1, identifier, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service3 = new Service(2, new ServiceIdentifier(), "Interior Cleaning", "2 hours", 80.00f, "/images/interior.jpg");

        // Act & Assert
        assertEquals(service1, service2);
        assertEquals(service1.hashCode(), service2.hashCode());
    }



    @Test
    void testSettersAndGetters() {
        // Arrange
        Service service = new Service();
        ServiceIdentifier identifier = new ServiceIdentifier();

        // Act
        service.setId(1);
        service.setServiceIdentifier(identifier);
        service.setServiceName("Car Detailing");
        service.setTimeRequired("3 hours");
        service.setPrice(100.50f);
        service.setImagePath("/images/detailing.jpg");

        // Assert
        assertEquals(service.getId(), 1);
        assertEquals(service.getServiceIdentifier(), identifier);
        assertEquals(service.getServiceName(), "Car Detailing");
        assertEquals(service.getTimeRequired(), "3 hours");
        assertEquals(service.getPrice(), 100.50f);
        assertEquals(service.getImagePath(), "/images/detailing.jpg");
    }

    @Test
    void testCanEqual() {
        // Arrange
        ServiceIdentifier identifier = new ServiceIdentifier();
        Service service1 = new Service(1, identifier, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service2 = new Service(1, identifier, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");

        // Act & Assert
        assertThat(service1.canEqual(service2)).isTrue();
        assertThat(service1.canEqual(new Object())).isFalse();
    }

    @Test
    void testConstructorWithAllArgs() {
        // Arrange
        ServiceIdentifier identifier = new ServiceIdentifier();
        Service service = new Service(1, identifier, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");

        // Assert
        assertEquals(service.getId(), 1);
        assertEquals(service.getServiceIdentifier(), identifier);
        assertEquals(service.getServiceName(), "Car Detailing");
        assertEquals(service.getTimeRequired(), "3 hours");
        assertEquals(service.getPrice(), 100.50f);
        assertEquals(service.getImagePath(), "/images/detailing.jpg");
    }

    @Test
    void testServiceBuilderToString() {
        // Arrange
        Service.ServiceBuilder builder = Service.builder()
                .id(1)
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Car Detailing")
                .timeRequired("3 hours")
                .price(100.50f)
                .imagePath("/images/detailing.jpg");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "1",
                "Car Detailing",
                "3 hours",
                "100.5",
                "/images/detailing.jpg"
        );
    }

    @Test
    void testServiceBuilderId() {
        // Arrange
        Service.ServiceBuilder builder = Service.builder();

        // Act
        builder.id(1);
        Service service = builder.build();

        // Assert
        assertThat(service.getId()).isEqualTo(1);
    }

    @Test
    void testServiceBuilderImagePath() {
        // Arrange
        Service.ServiceBuilder builder = Service.builder();

        // Act
        builder.imagePath("/images/detailing.jpg");
        Service service = builder.build();

        // Assert
        assertThat(service.getImagePath()).isEqualTo("/images/detailing.jpg");
    }

    @Test
    void testServiceBuilderPrice() {
        // Arrange
        Service.ServiceBuilder builder = Service.builder();

        // Act
        builder.price(100.50f);
        Service service = builder.build();

        // Assert
        assertThat(service.getPrice()).isEqualTo(100.50f);
    }

    @Test
    void testEquals() {
        // Arrange
        ServiceIdentifier identifier1 = new ServiceIdentifier();
        ServiceIdentifier identifier2 = new ServiceIdentifier();
        Service service1 = new Service(1, identifier1, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service2 = new Service(1, identifier1, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service3 = new Service(2, identifier2, "Paint Protection", "5 hours", 200.00f, "/images/paint.jpg");

        // Act & Assert
        assertThat(service1).isEqualTo(service2);
        assertThat(service1).isNotEqualTo(service3);
    }

    @Test
    void testHashCode() {
        // Arrange
        ServiceIdentifier identifier1 = new ServiceIdentifier();
        ServiceIdentifier identifier2 = new ServiceIdentifier();
        Service service1 = new Service(1, identifier1, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service2 = new Service(1, identifier1, "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        Service service3 = new Service(2, identifier2, "Paint Protection", "5 hours", 200.00f, "/images/paint.jpg");

        // Act & Assert
        assertThat(service1.hashCode()).isEqualTo(service2.hashCode());
        assertThat(service1.hashCode()).isNotEqualTo(service3.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Service service = new Service();
        ServiceIdentifier identifier = new ServiceIdentifier();

        // Act
        service.setId(1);
        service.setServiceIdentifier(identifier);
        service.setServiceName("Car Detailing");
        service.setTimeRequired("3 hours");
        service.setPrice(100.50f);
        service.setImagePath("/images/detailing.jpg");

        // Assert
        assertThat(service.getId()).isEqualTo(1);
        assertThat(service.getServiceIdentifier()).isEqualTo(identifier);
        assertThat(service.getServiceName()).isEqualTo("Car Detailing");
        assertThat(service.getTimeRequired()).isEqualTo("3 hours");
        assertThat(service.getPrice()).isEqualTo(100.50f);
        assertThat(service.getImagePath()).isEqualTo("/images/detailing.jpg");
    }
}