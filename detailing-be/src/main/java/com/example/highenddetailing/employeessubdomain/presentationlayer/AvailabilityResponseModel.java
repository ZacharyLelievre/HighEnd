package com.example.highenddetailing.employeessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponseModel {
    private String dayOfWeek; // Example: "Monday"
    private String startTime; // Example: "08:00"
    private String endTime;   // Example: "16:00"
}
