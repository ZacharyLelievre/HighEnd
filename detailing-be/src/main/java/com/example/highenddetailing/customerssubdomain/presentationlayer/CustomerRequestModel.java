package com.example.highenddetailing.customerssubdomain.presentationlayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestModel {

    String firstName;
    String lastName;
    String emailAddress;
    String streetAddress;
    String city;
    String postalCode;
    String province;
    String country;
}
