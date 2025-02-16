package com.example.highenddetailing.promotionsubdomain.datalayer;

import com.example.highenddetailing.servicessubdomain.datalayer.Service; // if referencing the Service entity
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to a service (assuming one promotion belongs to one service)
    @ManyToOne
    @JoinColumn(name = "service_id_fk", referencedColumnName = "id")
    private Service service;


    private float oldPrice;
    private float newPrice;

    private String discountMessage;
}
