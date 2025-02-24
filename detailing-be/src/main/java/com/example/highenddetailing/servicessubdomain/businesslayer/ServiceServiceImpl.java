package com.example.highenddetailing.servicessubdomain.businesslayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;
import com.example.highenddetailing.servicessubdomain.mapperlayer.ServiceResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceResponseMapper serviceResponseMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceResponseMapper serviceResponseMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceResponseMapper = serviceResponseMapper;
    }

    @Override
    public List<ServiceResponseModel> getAllServices(){
        return serviceResponseMapper.entityListToResponseModel(serviceRepository.findAll());
    }

    @Override
    public Optional<ServiceResponseModel> getServiceById(String serviceId) {
        return serviceRepository.findByServiceIdentifier_ServiceId(serviceId)
                .map(serviceResponseMapper::entityToResponseModel);
    }
    @Override
    public ServiceResponseModel createService(String serviceName, String timeRequired, float price, String imagePath) {
        Service service = new Service(serviceName, timeRequired, price, imagePath);
        Service saved = serviceRepository.save(service);
        return serviceResponseMapper.entityToResponseModel(saved);
    }
    @Override
    public void deleteService(String serviceId) {
        serviceRepository.findByServiceIdentifier_ServiceId(serviceId)
                .ifPresent(service -> {
                    serviceRepository.delete(service);
                    log.info("Deleted service with ID: " + serviceId);
                });
    }
}
