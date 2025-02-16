package com.example.highenddetailing.promotionsubdomain.businesslayer;


import com.example.highenddetailing.promotionsubdomain.datalayer.Promotion;
import com.example.highenddetailing.promotionsubdomain.datalayer.PromotionRepository;
import com.example.highenddetailing.promotionsubdomain.mapperlayer.PromotionMapper;
import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionRequestModel;
import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionResponseModel;
import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ServiceRepository serviceRepository;
    private final PromotionMapper promotionMapper;

    @Override
    public PromotionResponseModel createPromotion(PromotionRequestModel request) {
        // 1) Look up the service we’re promoting
        var serviceOpt = serviceRepository.findByServiceIdentifier_ServiceId(request.getServiceId());
        if (serviceOpt.isEmpty()) {
            throw new RuntimeException("Service not found with id: " + request.getServiceId());
        }
        Service service = serviceOpt.get();

        // 2) Create the Promotion entity with old/new prices
        Promotion promotion = Promotion.builder()
                .service(service)
                .oldPrice(service.getPrice())            // current DB price
                .newPrice(request.getNewPrice())         // from the request
                .discountMessage(request.getDiscountMessage())
                .build();

        // 3) Optionally, update the service's price in the Service table
        //    (Only do this if your business rules require the main service price to change!)
        service.setPrice(request.getNewPrice());
        serviceRepository.save(service);

        // 4) Save the promotion record
        Promotion saved = promotionRepository.save(promotion);

        // 5) Return a response model
        return promotionMapper.entityToResponseModel(saved);
    }

    @Override
    public List<PromotionResponseModel> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotionMapper.entityListToResponseModel(promotions);
    }

    @Override
    public void deletePromotion(String promotionId) {
        // Convert String to Long
        Long id = Long.valueOf(promotionId);

        // 1) Find the promotion by ID
        var promotionOpt = promotionRepository.findById(id);
        if (promotionOpt.isEmpty()) {
            throw new RuntimeException("Promotion not found with id: " + id);
        }

        // 2) Restore original price in the Service entity (if needed)
        Promotion promotion = promotionOpt.get();
        Service service = promotion.getService();
        service.setPrice(promotion.getOldPrice()); // Reset price
        serviceRepository.save(service);

        // 3) Delete the promotion
        promotionRepository.deleteById(id);
    }


}