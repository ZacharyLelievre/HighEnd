package com.example.highenddetailing.empployeessubdomain.businesslayer;

import com.example.highenddetailing.empployeessubdomain.presentationlayer.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();
}
