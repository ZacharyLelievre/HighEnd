package com.example.highenddetailing.servicessubdomain.businesslayer;

import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<ServiceResponseModel> getAllServices();

    Optional<ServiceResponseModel> getServiceById(String serviceId);
}
