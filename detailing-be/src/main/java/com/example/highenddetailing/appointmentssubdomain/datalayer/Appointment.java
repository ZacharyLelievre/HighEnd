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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    private AppointmentIdentifier appointmentIdentifier;
    private String customerId;
    private ServiceIdentifier serviceId;
    private String employeeId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String imagePath;

    public Appointment(Integer id, String customerId,
                       String employeeId,
                       String appointmentDate, String appointmentTime,
                       String status, String imagePath) {
        this.id = id;
        this.appointmentIdentifier = new AppointmentIdentifier();
        this.customerId = customerId;
        this.serviceId = new ServiceIdentifier();
        this.employeeId = employeeId;
        this.appointmentDate = LocalDate.parse(appointmentDate); // Convert String to LocalDate
        this.appointmentTime = LocalTime.parse(appointmentTime); // Convert String to LocalTime
        this.status = status;
        this.imagePath = imagePath;
    }
}