package com.example.highenddetailing.servicessubdomain.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class ServiceIdentifier {

    private String serviceId;

    public ServiceIdentifier() {this.serviceId = UUID.randomUUID().toString();
    }
}
