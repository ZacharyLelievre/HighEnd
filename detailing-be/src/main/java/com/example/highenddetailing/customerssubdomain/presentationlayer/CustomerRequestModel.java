package com.example.highenddetailing.customerssubdomain.presentationlayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestModel {

    String customerFirstName;
    String customerLastName;
    String customerEmailAddress;
    String streetAddress;
    String city;
    String postalCode;
    String province;
    String country;
    String auth0Sub;
}
