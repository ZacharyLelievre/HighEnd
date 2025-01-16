package com.example.highenddetailing.employeessubdomain.datalayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private EmployeeIdentifier employeeIdentifier;
    private String first_name;
    private String last_name;
    private String position;
    private String email;
    private String phone;
    private double salary;
    private String imagePath;

    // Parameterized constructor
    public Employee(Integer id, String first_name, String last_name, String position, String email, String phone, double salary, String imagePath) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.imagePath = imagePath;
    }
}