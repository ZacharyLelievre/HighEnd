package com.example.highenddetailing.servicessubdomain.utils;

import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.domainclientlayer.ServiceResponseModel;
import org.springframework.beans.BeanUtils;

public class EntityModelUtil {

    public static ServiceResponseModel toServiceResponseModel(Service service) {
        ServiceResponseModel servicesResponseModel = new ServiceResponseModel();
        BeanUtils.copyProperties(service, servicesResponseModel);
        return servicesResponseModel;
    }
}
