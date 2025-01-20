package com.example.highenddetailing.employeessubdomain.datalayer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    private String dayOfWeek;
    private String startTime; // Example: "08:00"
    private String endTime;   // Example: "16:00"
}
