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
    private String appointmentEndTime;
    private String serviceId;
    private String serviceName;
    private String customerId;
    private String customerName;
    private String employeeId;
    private String employeeName;
    private Status status;
    private String imagePath;
    private String customerEmailAddress;

}