package com.example.highenddetailing.servicessubdomain.mapperlayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T13:31:28-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class ServiceResponseMapperImpl implements ServiceResponseMapper {

    @Override
    public ServiceResponseModel entityToResponseModel(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceResponseModel.ServiceResponseModelBuilder serviceResponseModel = ServiceResponseModel.builder();

        serviceResponseModel.serviceId( service.getServiceIdentifier().getServiceId() );
        serviceResponseModel.serviceName( service.getServiceName() );
        serviceResponseModel.timeRequired( service.getTimeRequired() );
        serviceResponseModel.price( service.getPrice() );
        serviceResponseModel.imagePath( service.getImagePath() );

        return serviceResponseModel.build();
    }

    @Override
    public List<ServiceResponseModel> entityListToResponseModel(List<Service> service) {
        if ( service == null ) {
            return null;
        }

        List<ServiceResponseModel> list = new ArrayList<ServiceResponseModel>( service.size() );
        for ( Service service1 : service ) {
            list.add( entityToResponseModel( service1 ) );
        }

        return list;
    }
}
