package com.example.highenddetailing.appointmentssubdomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseModel {
    private String appointmentId;
    private String appointmentDate;
    private String appointmentTime;
    private String serviceId;
    private String customerId;
    private String employeeId;
    private String status;
    private String imagePath;
}