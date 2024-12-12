package com.example.highenddetailing.employeessubdomain.businesslayer;

import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();
}
