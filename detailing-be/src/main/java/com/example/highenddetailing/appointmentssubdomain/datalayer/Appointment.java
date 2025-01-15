package com.example.highenddetailing.appointmentssubdomain.datalayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private AppointmentIdentifier appointmentIdentifier;

    private String customerId;
    private String customerName;

    private String serviceId;
    private String serviceName;

    private String employeeId;
    private String employeeName;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalTime appointmentEndTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String imagePath;

    // Updated Constructor
    public Appointment(Integer id, String customerId, String customerName,
                       String employeeId, String employeeName,
                       String serviceId, String serviceName, // Added serviceId and serviceName
                       String appointmentDate, String appointmentTime,
                       String appointmentEndTime,
                       Status status, String imagePath) {
        this.id = id;
        this.appointmentIdentifier = new AppointmentIdentifier();
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.appointmentDate = LocalDate.parse(appointmentDate);
        this.appointmentTime = LocalTime.parse(appointmentTime);
        this.appointmentEndTime = LocalTime.parse(appointmentEndTime);
        this.status = status;
        this.imagePath = imagePath;
    }
}