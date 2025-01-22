package com.example.highenddetailing.employeessubdomain.mapperlayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    @Mapping(expression = "java(employee.getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(employee.getFirst_name())", target = "first_name")
    @Mapping(expression = "java(employee.getLast_name())", target = "last_name")
    @Mapping(expression = "java(employee.getPosition())", target = "position")
    @Mapping(expression = "java(employee.getEmail())", target = "email")
    @Mapping(expression = "java(employee.getSalary())", target = "salary")
    @Mapping(expression = "java(employee.getImagePath())", target = "imagePath")
    @Mapping(source = "availability", target = "availability") // Map availability list
    EmployeeResponseModel entityToResponseModel(Employee employee);

    List<EmployeeResponseModel> entityListToResponseModel(List<Employee> employees);

    // Add the mapping for Availability to AvailabilityResponseModel
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    AvailabilityResponseModel availabilityToAvailabilityResponseModel(Availability availability);

    // Map List<Availability> to List<AvailabilityResponseModel>
    List<AvailabilityResponseModel> availabilityListToResponseModel(List<Availability> availability);
}