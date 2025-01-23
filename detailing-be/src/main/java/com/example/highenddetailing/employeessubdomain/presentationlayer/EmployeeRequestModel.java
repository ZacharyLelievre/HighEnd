package com.example.highenddetailing.employeessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestModel {
    private String auth0Sub;     // ← same idea as 'auth0Sub' in Customer
    private String first_name;
    private String last_name;
    private String position;
    private String email;
    private String phone;
    private double salary;
    private String imagePath;
}
