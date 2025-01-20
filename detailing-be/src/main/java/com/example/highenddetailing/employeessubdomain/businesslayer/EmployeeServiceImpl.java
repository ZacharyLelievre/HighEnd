package com.example.highenddetailing.employeessubdomain.businesslayer;


import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeService;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
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
    public Optional<EmployeeResponseModel> getEmployeeById(String employeeIds){
        return employeeRepository.findByEmployeeId(employeeIds)
                .map(employeeResponseMapper::entityToResponseModel);
    }

    @Override
    public List<AvailabilityResponseModel> getAvailabilityForEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        log.debug("Employee found: {}", employee);
        log.debug("Employee availability: {}", employee.getAvailability());

        return employee.getAvailability().stream()
                .map(availability -> new AvailabilityResponseModel(
                        availability.getDayOfWeek(),
                        availability.getStartTime(),
                        availability.getEndTime()))
                .toList();
    }


}


