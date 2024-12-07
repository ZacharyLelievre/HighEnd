package com.example.highenddetailing.appointmentssubdomain;

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
    private String appointmentId;
    private String customerId;
    private String serviceId;
    private String employeeId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String imagePath;
}