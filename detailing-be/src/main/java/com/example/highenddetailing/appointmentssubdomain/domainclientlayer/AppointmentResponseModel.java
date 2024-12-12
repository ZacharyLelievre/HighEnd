package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
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
    private String serviceName; // New field
    private String customerId;
    private String customerName; // New field
    private String employeeId;
    private String employeeName; // New field
    private Status status;
    private String imagePath;
}