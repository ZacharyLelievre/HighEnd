package com.example.highenddetailing.promotionsubdomain.mapperlayer;

import com.example.highenddetailing.promotionsubdomain.datalayer.Promotion;

import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    // We assume we want to show the service’s serviceId in the response
    @Mapping(expression = "java(promotion.getId())",          target = "promotionId")
    @Mapping(expression = "java(promotion.getService().getServiceIdentifier().getServiceId())", target = "serviceId")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "discountMessage", target = "discountMessage")
    PromotionResponseModel entityToResponseModel(Promotion promotion);

    List<PromotionResponseModel> entityListToResponseModel(List<Promotion> promotions);
}