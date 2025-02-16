package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import com.example.highenddetailing.promotionsubdomain.datalayer.Promotion;
import com.example.highenddetailing.promotionsubdomain.mapperlayer.PromotionMapperImpl;
import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionMapperTest {

    private PromotionMapperImpl promotionMapper;

    @BeforeEach
    void setUp() {
        promotionMapper = new PromotionMapperImpl();
    }

    @Test
    void shouldMapEntityToResponseModel() {
        // Arrange
        Service service = new Service();
        service.setId(1);

        // ✅ Fix: Ensure ServiceIdentifier is set
        ServiceIdentifier serviceIdentifier = new ServiceIdentifier();
        service.setServiceIdentifier(serviceIdentifier);

        Promotion promotion = new Promotion();
        promotion.setId(100L);
        promotion.setService(service);
        promotion.setOldPrice(249.99f);
        promotion.setNewPrice(199.99f);
        promotion.setDiscountMessage("Spring Discount");

        // Act
        PromotionResponseModel response = promotionMapper.entityToResponseModel(promotion);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getPromotionId()).isEqualTo(100L);
        assertThat(response.getOldPrice()).isEqualTo(249.99f);
        assertThat(response.getNewPrice()).isEqualTo(199.99f);
        assertThat(response.getDiscountMessage()).isEqualTo("Spring Discount");
    }

    @Test
    void shouldMapEntityListToResponseModel() {
        // Arrange
        Service service = new Service();
        service.setId(2);
        ServiceIdentifier serviceIdentifier = new ServiceIdentifier();
        service.setServiceIdentifier(serviceIdentifier);

        Promotion promo1 = new Promotion();
        promo1.setId(101L);
        promo1.setService(service);
        promo1.setOldPrice(300.00f);
        promo1.setNewPrice(250.00f);
        promo1.setDiscountMessage("Promo 1");

        Promotion promo2 = new Promotion();
        promo2.setId(102L);
        promo2.setService(service);
        promo2.setOldPrice(400.00f);
        promo2.setNewPrice(350.00f);
        promo2.setDiscountMessage("Promo 2");

        List<Promotion> promotions = List.of(promo1, promo2);

        // Act
        List<PromotionResponseModel> responseList = promotionMapper.entityListToResponseModel(promotions);

        // Assert
        assertThat(responseList).isNotNull();
        assertThat(responseList).hasSize(2);

        assertThat(responseList.get(0).getPromotionId()).isEqualTo(101L);
        assertThat(responseList.get(0).getOldPrice()).isEqualTo(300.00f);
        assertThat(responseList.get(0).getNewPrice()).isEqualTo(250.00f);
        assertThat(responseList.get(0).getDiscountMessage()).isEqualTo("Promo 1");

        assertThat(responseList.get(1).getPromotionId()).isEqualTo(102L);
        assertThat(responseList.get(1).getOldPrice()).isEqualTo(400.00f);
        assertThat(responseList.get(1).getNewPrice()).isEqualTo(350.00f);
        assertThat(responseList.get(1).getDiscountMessage()).isEqualTo("Promo 2");
    }
}