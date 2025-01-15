package com.example.highenddetailing.customerssubdomain.datalayer;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class CustomerIdentifier {

    private String customerId;

    // Modified constructor to accept auth0UserId directly
    public CustomerIdentifier(String auth0UserId) {
        this.customerId = auth0UserId;
    }

    public CustomerIdentifier() {
        // Default constructor for JPA
    }
}