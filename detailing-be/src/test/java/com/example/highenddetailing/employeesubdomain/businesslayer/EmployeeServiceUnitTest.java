package com.example.highenddetailing.employeesubdomain.businesslayer;


import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeServiceImpl;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeResponseMapper employeeResponseMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void whenGetAllEmployees_thenReturnAllEmployees() {
        // Arrange
        List<Employee> employees = List.of(
                new Employee(1,
                        new EmployeeIdentifier(),
                        "Jane",
                        "Smith",
                        "Manager",
                        "jane.smith@example.com",
                        75000.00,
                        "profile.png"
                ),
                new Employee(2,
                        new EmployeeIdentifier(),
                        "John",
                        "Doe",
                        "Technician",
                        "john.doe@example.com",
                        55000.00,
                        "profile.png"
                )
        );


        List<EmployeeResponseModel> responseModels = List.of(
                new EmployeeResponseModel(
                        "e1f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        "Jane",
                        "Smith",
                        "Manager",
                        "jane.smith@example.com",
                        75000.00,
                        "profile.png"
                ),
                new EmployeeResponseModel(
                        "e2f14c90-ec5e-4f82-a9b7-2548a7325b34",
                        "John",
                        "Doe",
                        "Technician",
                        "john.doe@example.com",
                        55000.00,
                        "profile.png"
                )
        );

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeResponseMapper.entityListToResponseModel(employees)).thenReturn(responseModels);

        // Act
        List<EmployeeResponseModel> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(responseModels, result);
    }
}