package com.example.highenddetailing.empployeessubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseModel {

    private String employeeId;
    private String first_name;
    private String last_name;
    private String position;
    private String email;
    private double salary;
    private String imagePath;
}
