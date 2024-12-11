package com.example.highenddetailing.empployeessubdomain.mapperlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.empployeessubdomain.datalayer.Employee;
import com.example.highenddetailing.empployeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.empployeessubdomain.presentationlayer.EmployeeResponseModel;
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
    EmployeeResponseModel entityToResponseModel(Employee employee);

    List<EmployeeResponseModel> entityListToResponseModel(List<Employee> employees);

}