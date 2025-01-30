package com.example.highenddetailing.employeessubdomain.businesslayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();

    Optional<EmployeeResponseModel> getEmployeeById(String employeeId);

    //Availability

    List<AvailabilityResponseModel> getAvailabilityForEmployee(String employeeId);

    void setAvailabilityForEmployee(String employeeId, List<Availability> newAvailability);

    EmployeeResponseModel createEmployee(EmployeeRequestModel request);
    EmployeeResponseModel getCurrentEmployee(String auth0UserId);
}
