package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionResponseModelTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        PromotionResponseModel response = new PromotionResponseModel();

        // Act
        response.setPromotionId(1L);
        response.setServiceId("service-123");
        response.setOldPrice(100.0f);
        response.setNewPrice(75.0f);
        response.setDiscountMessage("Special Discount");

        // Assert
        assertEquals(1L, response.getPromotionId());
        assertEquals("service-123", response.getServiceId());
        assertEquals(100.0f, response.getOldPrice());
        assertEquals(75.0f, response.getNewPrice());
        assertEquals("Special Discount", response.getDiscountMessage());
    }

    @Test
    void testBuilderPattern() {
        // Arrange & Act
        PromotionResponseModel response = PromotionResponseModel.builder()
                .promotionId(2L)
                .serviceId("service-456")
                .oldPrice(150.0f)
                .newPrice(120.0f)
                .discountMessage("Limited Offer")
                .build();

        // Assert
        assertEquals(2L, response.getPromotionId());
        assertEquals("service-456", response.getServiceId());
        assertEquals(150.0f, response.getOldPrice());
        assertEquals(120.0f, response.getNewPrice());
        assertEquals("Limited Offer", response.getDiscountMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PromotionResponseModel response1 = new PromotionResponseModel(1L, "service-123", 100.0f, 80.0f, "Black Friday Sale");
        PromotionResponseModel response2 = new PromotionResponseModel(1L, "service-123", 100.0f, 80.0f, "Black Friday Sale");
        PromotionResponseModel response3 = new PromotionResponseModel(2L, "service-456", 200.0f, 150.0f, "New Year Sale");

        // Act & Assert
        assertThat(response1).isEqualTo(response2);
        assertThat(response1).isNotEqualTo(response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        PromotionResponseModel response = new PromotionResponseModel(3L, "service-789", 300.0f, 250.0f, "Summer Discount");

        // Act
        String toStringResult = response.toString();

        // Assert
        assertThat(toStringResult).contains("3", "service-789", "300.0", "250.0", "Summer Discount");
    }

    @Test
    void testCanEqual() {
        // Arrange
        PromotionResponseModel response1 = new PromotionResponseModel(4L, "service-999", 400.0f, 350.0f, "Holiday Discount");
        PromotionResponseModel response2 = new PromotionResponseModel(4L, "service-999", 400.0f, 350.0f, "Holiday Discount");

        // Act & Assert
        assertTrue(response1.canEqual(response2));
        assertFalse(response1.canEqual(new Object()));
    }
}
