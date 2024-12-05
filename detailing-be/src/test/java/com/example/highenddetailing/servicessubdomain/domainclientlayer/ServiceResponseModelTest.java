package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceResponseModelTest {

    @Test
    void testServiceResponseModelBuilder() {
        // Arrange
        String serviceId = "S001";
        String serviceName = "Car Detailing";
        String timeRequired = "3 hours";
        float price = 100.50f;
        String imagePath = "/images/detailing.jpg";

        // Act
        ServiceResponseModel responseModel = ServiceResponseModel.builder()
                .serviceId(serviceId)
                .serviceName(serviceName)
                .timeRequired(timeRequired)
                .price(price)
                .imagePath(imagePath)
                .build();

        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getServiceName(), serviceName);
        assertEquals(responseModel.getTimeRequired(), timeRequired);
        assertEquals(responseModel.getPrice(), price);
        assertEquals(responseModel.getImagePath(), imagePath);
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        ServiceResponseModel responseModel = new ServiceResponseModel();

        // Assert
        assertThat(responseModel.getServiceId()).isNull();
        assertThat(responseModel.getServiceName()).isNull();
        assertThat(responseModel.getTimeRequired()).isNull();
        assertThat(responseModel.getPrice()).isZero();
        assertThat(responseModel.getImagePath()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String serviceId = "S001";
        String serviceName = "Car Detailing";
        String timeRequired = "3 hours";
        float price = 100.50f;
        String imagePath = "/images/detailing.jpg";

        // Act
        ServiceResponseModel responseModel = new ServiceResponseModel(serviceId, serviceName, timeRequired, price, imagePath);

        // Assert
        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getServiceName(), serviceName);
        assertEquals(responseModel.getTimeRequired(), timeRequired);
        assertEquals(responseModel.getPrice(), price);
        assertEquals(responseModel.getImagePath(), imagePath);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ServiceResponseModel model1 = new ServiceResponseModel("S001", "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        ServiceResponseModel model2 = new ServiceResponseModel("S001", "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        ServiceResponseModel model3 = new ServiceResponseModel("S002", "Interior Cleaning", "2 hours", 80.00f, "/images/interior.jpg");

        // Act & Assert
        assertThat(model1).isEqualTo(model2);
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode());
        assertThat(model1).isNotEqualTo(model3);
        assertThat(model1.hashCode()).isNotEqualTo(model3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        ServiceResponseModel.ServiceResponseModelBuilder builder = ServiceResponseModel.builder();
        builder.serviceId("123")
                .serviceName("Car Detailing")
                .timeRequired("3 hours")
                .price(100.50f)
                .imagePath("/images/detailing.jpg");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "serviceId=123",
                "serviceName=Car Detailing",
                "timeRequired=3 hours",
                "price=100.5",
                "imagePath=/images/detailing.jpg"
        );
    }
    @Test
    void testSetters() {
        // Arrange
        ServiceResponseModel model = new ServiceResponseModel();

        // Act
        model.setServiceId("S001");
        model.setServiceName("Car Detailing");
        model.setTimeRequired("3 hours");
        model.setPrice(100.50f);
        model.setImagePath("/images/detailing.jpg");

        // Assert
        assertThat(model.getServiceId()).isEqualTo("S001");
        assertThat(model.getServiceName()).isEqualTo("Car Detailing");
        assertThat(model.getTimeRequired()).isEqualTo("3 hours");
        assertThat(model.getPrice()).isEqualTo(100.50f);
        assertThat(model.getImagePath()).isEqualTo("/images/detailing.jpg");
    }

    @Test
    void testCanEqual() {
        // Arrange
        ServiceResponseModel model1 = new ServiceResponseModel("S001", "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");
        ServiceResponseModel model2 = new ServiceResponseModel("S001", "Car Detailing", "3 hours", 100.50f, "/images/detailing.jpg");

        // Act & Assert
        assertThat(model1.canEqual(model2)).isTrue();
        assertThat(model1.canEqual(new Object())).isFalse();
    }

    @Test
    void testBuild() {
        // Arrange
        ServiceResponseModel.ServiceResponseModelBuilder builder = ServiceResponseModel.builder();
        builder.serviceId("123")
                .serviceName("Car Detailing")
                .timeRequired("3 hours")
                .price(100.50f)
                .imagePath("/images/detailing.jpg");

        // Act
        ServiceResponseModel model = builder.build();

        // Assert
        assertThat(model.getServiceId()).isEqualTo("123");
        assertThat(model.getServiceName()).isEqualTo("Car Detailing");
        assertThat(model.getTimeRequired()).isEqualTo("3 hours");
        assertThat(model.getPrice()).isEqualTo(100.50f);
        assertThat(model.getImagePath()).isEqualTo("/images/detailing.jpg");
    }

}