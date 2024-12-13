package com.example.highenddetailing.empployeessubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.empployeessubdomain.datalayer.Employee;
import com.example.highenddetailing.empployeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.empployeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.empployeessubdomain.presentationlayer.EmployeeResponseModel;
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


