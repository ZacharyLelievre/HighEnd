package com.example.highenddetailing.customerssubdomain.datalayer;

import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Data
public class Address {

    private String streetAddress;
    private String city;
    private String postalCode;
    private String province;
    private String country;


}
