package com.example.highenddetailing.servicessubdomain.domainclientlayer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponseModel {

    private String serviceId;
    private String serviceName;
    private String timeRequired;
    private float price;
    private String imagePath;
}
