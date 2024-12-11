package com.example.highenddetailing.customerssubdomain.datalayer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Address {

    private String streetAddress;
    private String city;
    private String postalCode;
    private String province;
    private String country;
}
