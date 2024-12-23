package com.example.highenddetailing.customerssubdomain.datalayer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "customerId", column = @Column(name = "customer_id", unique = true, nullable = false))
    })
    private CustomerIdentifier customerIdentifier;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmailAddress;
    @Embedded
    private Address address;


    public Customer(CustomerIdentifier customerIdentifier, String customerFirstName, String customerLastName, String customerEmailAddress, Address address) {
        this.customerIdentifier = customerIdentifier;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmailAddress = customerEmailAddress;
        this.address = address;
    }
}
