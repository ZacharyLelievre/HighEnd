package com.example.highenddetailing.employeessubdomain.businesslayer;

import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();

    Optional<EmployeeResponseModel> getEmployeeById(String employeeId);

}
