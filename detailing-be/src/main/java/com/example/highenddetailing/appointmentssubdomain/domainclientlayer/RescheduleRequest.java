package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleRequest {
    private String newDate;
    private String newStartTime;
    private String newEndTime;
}
