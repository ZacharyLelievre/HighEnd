package com.example.highenddetailing.servicessubdomain.businesslayer;

import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;
import com.example.highenddetailing.servicessubdomain.mapperlayer.ServiceResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
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
}
