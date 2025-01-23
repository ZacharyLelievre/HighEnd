package com.example.highenddetailing.employeessubdomain.businesslayer;


import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeService;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
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
        return employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeIds)
                .map(employeeResponseMapper::entityToResponseModel);
    }

    @Override
    public List<AvailabilityResponseModel> getAvailabilityForEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId)
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

    @Override
    public void setAvailabilityForEmployee(String employeeId, List<Availability> newAvailability) {
        Employee employee = employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // You can choose to either replace the entire availability list
        // or you can manipulate it further (e.g., add new slots).
        employee.setAvailability(newAvailability);

        // Save changes to DB
        employeeRepository.save(employee);
    }
    @Override
    public EmployeeResponseModel createEmployee(EmployeeRequestModel employeeRequestModel, String auth0UserId) {
        // 1. Check if employee already exists (employeeId = sub)
        Optional<Employee> existing = employeeRepository.findByEmployeeIdentifier_EmployeeId(auth0UserId);
        if (existing.isPresent()) {
            // If yes, return existing record instead of making a new one
            return employeeResponseMapper.entityToResponseModel(existing.get());
        }

        EmployeeIdentifier employeeIdentifier = new EmployeeIdentifier(auth0UserId);

        Employee newEmployee = Employee.builder()
                .employeeIdentifier(employeeIdentifier)
                .first_name(employeeRequestModel.getFirst_name())
                .last_name(employeeRequestModel.getLast_name())
                .position(employeeRequestModel.getPosition())
                .email(employeeRequestModel.getEmail())
                .phone(employeeRequestModel.getPhone())
                .salary(employeeRequestModel.getSalary())
                .imagePath(employeeRequestModel.getImagePath())
                .build();

        Employee savedEmployee = employeeRepository.save(newEmployee);
        return employeeResponseMapper.entityToResponseModel(savedEmployee);
    }


}


