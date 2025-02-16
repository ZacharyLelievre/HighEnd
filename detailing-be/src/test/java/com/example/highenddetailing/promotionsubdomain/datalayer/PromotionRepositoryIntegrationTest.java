package com.example.highenddetailing.promotionsubdomain.datalayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PromotionRepositoryIntegrationTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    void whenFindAll_thenReturnAllPromotions() {
        // Arrange: create & save a Service first, because each Promotion references a Service
        Service service = Service.builder()
                .serviceIdentifier(new ServiceIdentifier())  // sets a random UUID
                .serviceName("Test Service")
                .timeRequired("30 minutes")
                .price(99.99f)
                .imagePath("test.png")
                .build();
        serviceRepository.saveAndFlush(service);

        // Create and save multiple Promotions referencing the same Service
        Promotion promo1 = Promotion.builder()
                .service(service)
                .oldPrice(99.99f)
                .newPrice(79.99f)
                .discountMessage("Promo 1")
                .build();

        Promotion promo2 = Promotion.builder()
                .service(service)
                .oldPrice(99.99f)
                .newPrice(69.99f)
                .discountMessage("Promo 2")
                .build();

        promotionRepository.save(promo1);
        promotionRepository.save(promo2);

        // Act: fetch all promotions
        List<Promotion> promotions = promotionRepository.findAll();

        // Assert
        assertNotNull(promotions);
        assertEquals(2, promotions.size(), "Should return exactly 2 promotions");

        // Basic checks on the first promotion
        Promotion fetched1 = promotions.get(0);
        assertNotNull(fetched1.getId(), "Promotion ID should be auto-generated");
        assertEquals(service.getId(), fetched1.getService().getId(), "Promotion should link to the correct Service");
        // e.g. check the discount message, etc.

        // Basic checks on the second promotion
        Promotion fetched2 = promotions.get(1);
        assertNotNull(fetched2.getId());
        assertEquals(service.getId(), fetched2.getService().getId());
        // etc.
    }

    @Test
    void whenDeletePromotion_thenPromotionIsRemoved() {
        // Arrange: create & save a Service
        Service service = Service.builder()
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Test Service")
                .timeRequired("30 minutes")
                .price(99.99f)
                .imagePath("test.png")
                .build();
        serviceRepository.saveAndFlush(service);

        // Create a single Promotion referencing that Service
        Promotion promo = Promotion.builder()
                .service(service)
                .oldPrice(99.99f)
                .newPrice(79.99f)
                .discountMessage("Special Deal")
                .build();
        promo = promotionRepository.saveAndFlush(promo);

        Long promoId = promo.getId();
        assertNotNull(promoId, "Promotion ID should be assigned after saving");
        assertTrue(promotionRepository.findById(promoId).isPresent(), "Promotion should exist before deletion");

        // Act: delete the Promotion
        promotionRepository.delete(promo);
        promotionRepository.flush();  // ensure deletion is committed

        // Assert: verify the Promotion no longer exists
        Optional<Promotion> deletedPromo = promotionRepository.findById(promoId);
        assertFalse(deletedPromo.isPresent(), "Promotion should be removed after deletion");
    }
}
