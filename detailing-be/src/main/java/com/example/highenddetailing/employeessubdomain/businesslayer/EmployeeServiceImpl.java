package com.example.highenddetailing.employeessubdomain.businesslayer;


import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeService;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponseModel> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeResponseMapper.entityListToResponseModel(employees);
    }

    @Override
    public Optional<EmployeeResponseModel> getEmployeeById(String employeeId){
        return employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId)
                .map(employeeResponseMapper::entityToResponseModel);
    }
}


