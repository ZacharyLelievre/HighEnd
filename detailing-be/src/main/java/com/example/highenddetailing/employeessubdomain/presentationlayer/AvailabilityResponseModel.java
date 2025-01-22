package com.example.highenddetailing.employeessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponseModel {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
