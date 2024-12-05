package com.example.highenddetailing.servicessubdomain.datalayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ServiceIdentifier serviceIdentifier;
    private String serviceName;
    private String timeRequired;
    private float price;
    private String imagePath;


    public Service(String serviceName, String timeRequired, float price, String imagePath){
        this.serviceIdentifier = new ServiceIdentifier();
        this.serviceName = serviceName;
        this.timeRequired = timeRequired;
        this.price = price;
        this.imagePath = imagePath;
    }
}
