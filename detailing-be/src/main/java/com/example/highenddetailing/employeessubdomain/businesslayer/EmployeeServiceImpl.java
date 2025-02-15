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
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Override
    public void setAvailabilityForEmployee(String employeeId, List<Availability> newAvailability) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // You can choose to either replace the entire availability list
        // or you can manipulate it further (e.g., add new slots).
        employee.setAvailability(newAvailability);

        // Save changes to DB
        employeeRepository.save(employee);
    }
    @Override
    public EmployeeResponseModel createEmployee(EmployeeRequestModel request) {
        Employee newEmployee = Employee.builder()
                .employeeId(request.getEmployeeId())
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .position(request.getPosition())
                .email(request.getEmail())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .imagePath(request.getImagePath())
                .build();

        Employee savedEmployee = employeeRepository.save(newEmployee);

        return employeeResponseMapper.entityToResponseModel(savedEmployee);
    }
    @Override
    public EmployeeResponseModel getCurrentEmployee(String auth0UserId) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmployeeId(auth0UserId);
        return employeeOpt.map(employeeResponseMapper::entityToResponseModel).orElse(null);
    }


    public List<EmployeeResponseModel> findEmployeesAvailable(LocalDate date, LocalTime start, LocalTime end) {
        String dayOfWeek = date.getDayOfWeek().toString();
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> matching = new ArrayList<>();

        for (Employee emp : allEmployees) {
            boolean canDoIt = false;
            if (emp.getAvailability() != null) {
                for (Availability slot : emp.getAvailability()) {
                    if (slot.getDayOfWeek().equalsIgnoreCase(dayOfWeek)) {
                        LocalTime slotStart = LocalTime.parse(slot.getStartTime());
                        LocalTime slotEnd = LocalTime.parse(slot.getEndTime());
                        if (!slotStart.isAfter(start) && !slotEnd.isBefore(end)) {
                            canDoIt = true;
                            break;
                        }
                    }
                }
            }
            if (canDoIt) {
                matching.add(emp);
            }
        }

        return employeeResponseMapper.entityListToResponseModel(matching);
    }

}
