package com.example.highenddetailing.employeessubdomain.mapperlayer;

import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    @Mapping(expression = "java(employee.getEmployeeIdentifier().getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(employee.getFirst_name())", target = "first_name")
    @Mapping(expression = "java(employee.getLast_name())", target = "last_name")
    @Mapping(expression = "java(employee.getPosition())", target = "position")
    @Mapping(expression = "java(employee.getEmail())", target = "email")
    @Mapping(expression = "java(employee.getSalary())", target = "salary")
    @Mapping(expression = "java(employee.getImagePath())", target = "imagePath")
    @Mapping(source = "availability", target = "availability")
    EmployeeResponseModel entityToResponseModel(Employee employee);

    List<EmployeeResponseModel> entityListToResponseModel(List<Employee> employees);

}