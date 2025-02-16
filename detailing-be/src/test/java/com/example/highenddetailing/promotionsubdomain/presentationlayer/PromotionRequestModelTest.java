package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PromotionRequestModelTest {
    @Test
    void testSettersAndGetters() {
        // Arrange
        PromotionRequestModel request = new PromotionRequestModel();

        // Act
        request.setServiceId("service-123");
        request.setNewPrice(99.99f);
        request.setDiscountMessage("Special Offer");

        // Assert
        assertEquals("service-123", request.getServiceId());
        assertEquals(99.99f, request.getNewPrice());
        assertEquals("Special Offer", request.getDiscountMessage());
    }

    @Test
    void testBuilderPattern() {
        // Arrange & Act
        PromotionRequestModel request = PromotionRequestModel.builder()
                .serviceId("service-123")
                .newPrice(50.00f)
                .discountMessage("Limited Time Discount")
                .build();

        // Assert
        assertEquals("service-123", request.getServiceId());
        assertEquals(50.00f, request.getNewPrice());
        assertEquals("Limited Time Discount", request.getDiscountMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PromotionRequestModel request1 = new PromotionRequestModel("service-123", 75.0f, "Winter Sale");
        PromotionRequestModel request2 = new PromotionRequestModel("service-123", 75.0f, "Winter Sale");
        PromotionRequestModel request3 = new PromotionRequestModel("service-456", 99.0f, "Summer Sale");

        // Act & Assert
        assertThat(request1).isEqualTo(request2);
        assertThat(request1).isNotEqualTo(request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        PromotionRequestModel request = new PromotionRequestModel("service-123", 49.99f, "Black Friday Sale");

        // Act
        String toStringResult = request.toString();

        // Assert
        assertThat(toStringResult).contains("service-123", "49.99", "Black Friday Sale");
    }

    @Test
    void testCanEqual() {
        // Arrange
        PromotionRequestModel request1 = new PromotionRequestModel("service-123", 80.0f, "Holiday Sale");
        PromotionRequestModel request2 = new PromotionRequestModel("service-123", 80.0f, "Holiday Sale");

        // Act & Assert
        assertTrue(request1.canEqual(request2));
        assertFalse(request1.canEqual(new Object()));
    }
}

