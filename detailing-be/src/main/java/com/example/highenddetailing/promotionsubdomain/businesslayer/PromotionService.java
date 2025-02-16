package com.example.highenddetailing.promotionsubdomain.businesslayer;



import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionRequestModel;
import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionResponseModel;

import java.util.List;

public interface PromotionService {
    PromotionResponseModel createPromotion(PromotionRequestModel request);
    List<PromotionResponseModel> getAllPromotions();

}
