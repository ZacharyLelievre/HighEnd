package com.example.highenddetailing.empployeessubdomain.businesslayer;

import com.example.highenddetailing.empployeessubdomain.presentationlayer.EmployeeResponseModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();

    Optional<EmployeeResponseModel> getEmployeeById(String employeeId);
}
