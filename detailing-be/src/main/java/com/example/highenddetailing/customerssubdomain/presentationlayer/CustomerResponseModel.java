package com.example.highenddetailing.customerssubdomain.presentationlayer;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseModel {

    String customerId;
    String customerFirstName;
    String customerLastName;
    String customerEmailAddress;
    String streetAddress;
    String city;
    String postalCode;
    String province;
    String country;



}
