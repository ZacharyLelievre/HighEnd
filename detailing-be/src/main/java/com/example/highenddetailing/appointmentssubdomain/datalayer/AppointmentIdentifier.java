package com.example.highenddetailing.appointmentssubdomain.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;


import java.util.UUID;


@Embeddable
@Getter
public class AppointmentIdentifier {
    private String appointmentId;

    public AppointmentIdentifier() {
        this.appointmentId = UUID.randomUUID().toString();
    }
}
