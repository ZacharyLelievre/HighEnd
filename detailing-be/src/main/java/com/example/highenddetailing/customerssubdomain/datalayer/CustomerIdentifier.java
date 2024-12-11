package com.example.highenddetailing.customerssubdomain.datalayer;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
public class CustomerIdentifier {

    private String customerId;

    public CustomerIdentifier() {
        this.customerId = UUID.randomUUID().toString();
    }
}
