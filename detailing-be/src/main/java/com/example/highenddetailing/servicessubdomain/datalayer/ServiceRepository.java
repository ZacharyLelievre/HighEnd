package com.example.highenddetailing.servicessubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository <Service, Integer> {

    Optional<Service> findByServiceIdentifier_ServiceId(String serviceId);


}
