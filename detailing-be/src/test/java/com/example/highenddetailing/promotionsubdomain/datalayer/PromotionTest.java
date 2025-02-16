package com.example.highenddetailing.promotionsubdomain.datalayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionTest {

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        // Create a sample Service entity
        Service sampleService = Service.builder()
                .id(1)
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Sample Service")
                .timeRequired("30 minutes")
                .price(249.99f)
                .imagePath("sample.png")
                .build();

        // First Promotion
        Promotion promo1 = new Promotion();
        promo1.setId(100L);
        promo1.setService(sampleService);
        promo1.setOldPrice(249.99f);
        promo1.setNewPrice(199.99f);
        promo1.setDiscountMessage("Summer special!");

        // Second Promotion (identical to first)
        Promotion promo2 = new Promotion();
        promo2.setId(100L);
        promo2.setService(sampleService);
        promo2.setOldPrice(249.99f);
        promo2.setNewPrice(199.99f);
        promo2.setDiscountMessage("Summer special!");

        // Third Promotion (different ID, different newPrice)
        Promotion promo3 = new Promotion();
        promo3.setId(101L);
        promo3.setService(sampleService);
        promo3.setOldPrice(249.99f);
        promo3.setNewPrice(180.0f);
        promo3.setDiscountMessage("Another deal");

        // Act & Assert
        // promo1 and promo2 should be equal
        assertThat(promo1).isEqualTo(promo2);
        assertEquals(promo1.hashCode(), promo2.hashCode());

        // promo1 should not equal promo3
        assertThat(promo1).isNotEqualTo(promo3);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Promotion promotion = new Promotion();
        Service sampleService = new Service();
        sampleService.setId(1);

        // Act
        promotion.setId(999L);
        promotion.setService(sampleService);
        promotion.setOldPrice(249.99f);
        promotion.setNewPrice(199.99f);
        promotion.setDiscountMessage("Special Offer");

        // Assert
        assertEquals(999L, promotion.getId());
        assertEquals(sampleService, promotion.getService());
        assertEquals(249.99f, promotion.getOldPrice());
        assertEquals(199.99f, promotion.getNewPrice());
        assertEquals("Special Offer", promotion.getDiscountMessage());
    }

    @Test
    void testConstructorWithAllArgs() {
        // Arrange
        Service sampleService = Service.builder()
                .id(2)
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Engine Cleaning")
                .timeRequired("1 hour")
                .price(129.99f)
                .imagePath("engine.png")
                .build();

        Promotion promotion = new Promotion(
                200L,
                sampleService,
                129.99f,
                99.99f,
                "Engine Special"
        );

        // Assert
        assertEquals(200L, promotion.getId());
        assertEquals(sampleService, promotion.getService());
        assertEquals(129.99f, promotion.getOldPrice());
        assertEquals(99.99f, promotion.getNewPrice());
        assertEquals("Engine Special", promotion.getDiscountMessage());
    }

    @Test
    void testPromotionBuilderToString() {
        // Arrange
        Service sampleService = new Service();
        sampleService.setId(3);

        Promotion promotion = Promotion.builder()
                .id(300L)
                .service(sampleService)
                .oldPrice(250f)
                .newPrice(225f)
                .discountMessage("Builder Promo")
                .build();

        // Act
        String toStringResult = promotion.toString();

        // Assert
        // Since Lombok @Data is used, toString() includes field values
        assertThat(toStringResult).contains(
                "300",
                "Builder Promo",
                "250.0",
                "225.0"
        );
    }

    @Test
    void testPromotionBuilderId() {
        // Arrange
        Promotion promotion = Promotion.builder()
                .id(400L)
                .build();

        // Act & Assert
        assertThat(promotion.getId()).isEqualTo(400L);
    }

    @Test
    void testPromotionBuilderNewPrice() {
        // Arrange
        Promotion promotion = Promotion.builder()
                .newPrice(149.99f)
                .build();

        // Act & Assert
        assertThat(promotion.getNewPrice()).isEqualTo(149.99f);
    }

    @Test
    void testEquals() {
        // Arrange
        Service serviceA = new Service();
        serviceA.setId(1);

        Service serviceB = new Service();
        serviceB.setId(2);

        Promotion promo1 = new Promotion();
        promo1.setId(500L);
        promo1.setService(serviceA);

        Promotion promo2 = new Promotion();
        promo2.setId(500L);
        promo2.setService(serviceA);

        Promotion promo3 = new Promotion();
        promo3.setId(501L);
        promo3.setService(serviceB);

        // Act & Assert
        assertThat(promo1).isEqualTo(promo2);
        assertThat(promo1).isNotEqualTo(promo3);
    }

    @Test
    void testHashCode() {
        // Arrange
        Service serviceA = new Service();
        serviceA.setId(1);

        Service serviceB = new Service();
        serviceB.setId(2);

        Promotion promo1 = new Promotion();
        promo1.setId(600L);
        promo1.setService(serviceA);

        Promotion promo2 = new Promotion();
        promo2.setId(600L);
        promo2.setService(serviceA);

        Promotion promo3 = new Promotion();
        promo3.setId(601L);
        promo3.setService(serviceB);

        // Act & Assert
        assertThat(promo1.hashCode()).isEqualTo(promo2.hashCode());
        assertThat(promo1.hashCode()).isNotEqualTo(promo3.hashCode());
    }
}
