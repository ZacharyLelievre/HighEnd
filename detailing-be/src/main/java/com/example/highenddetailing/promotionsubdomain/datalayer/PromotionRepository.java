package com.example.highenddetailing.promotionsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    // e.g. find all promotions for a given service
    // List<Promotion> findByService_ServiceIdentifier_ServiceId(String serviceId);
}
