package com.example.highenddetailing.employeessubdomain.datalayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id  // Make this the real PK
    private String employeeId;

    private String first_name;
    private String last_name;
    private String position;
    private String email;
    private String phone;
    private double salary;
    private String imagePath;

    @ElementCollection
    @CollectionTable(
            name = "employee_availability",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Availability> availability;

    // If you need a constructor, you can pass the employeeId string
    public Employee(String employeeId, String first_name, String last_name, String position, String email,
                    String phone, double salary, String imagePath) {
        this.employeeId = employeeId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.imagePath = imagePath;
    }
}
