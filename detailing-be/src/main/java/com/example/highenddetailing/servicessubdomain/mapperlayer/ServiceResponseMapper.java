package com.example.highenddetailing.servicessubdomain.mapperlayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper {

    @Mapping(expression = "java(service.getServiceIdentifier().getServiceId())", target = "serviceId")
    @Mapping(expression = "java(service.getServiceName())", target = "serviceName")
    @Mapping(expression = "java(service.getTimeRequired())", target = "timeRequired")
    @Mapping(expression = "java(service.getPrice())", target = "price")
    @Mapping(expression = "java(service.getImagePath())", target = "imagePath")
    ServiceResponseModel entityToResponseModel(Service service);
    List<ServiceResponseModel> entityListToResponseModel(List<Service> service);
}
