package com.example.highenddetailing.employeesubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeIdentifier;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeResponseMapperTest {

    private final EmployeeResponseMapper mapper = Mappers.getMapper(EmployeeResponseMapper.class);

    @Test
    void testEntityToResponseModel() {
        // Mock Employee with String employeeId
        Employee employee = Employee.builder()
                .employeeId("emp123")
                .first_name("John")
                .last_name("Doe")
                .position("Manager")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(75000.00)
                .imagePath("/images/john.png")
                .availability(List.of(
                        new Availability("Monday", "08:00", "16:00"),
                        new Availability("Wednesday", "09:00", "17:00")
                ))
                .build();

        // Perform Mapping
        EmployeeResponseModel response = mapper.entityToResponseModel(employee);

        // Assertions
        assertNotNull(response);
        assertEquals("emp123", response.getEmployeeId());
        assertEquals("John", response.getFirst_name());
        assertEquals("Monday", response.getAvailability().get(0).getDayOfWeek());
        assertEquals("08:00", response.getAvailability().get(0).getStartTime());
    }

    @Test
    void testEntityListToResponseModel() {
        // Mock List of Employees
        Employee employee1 = Employee.builder()
                .employeeId("emp123")
                .first_name("John")
                .last_name("Doe")
                .position("Manager")
                .email("john.doe@example.com")
                .phone("1234567890")
                .salary(75000.00)
                .imagePath("/images/john.png")
                .availability(List.of(
                        new Availability("Monday", "08:00", "16:00")
                ))
                .build();

        Employee employee2 = Employee.builder()
                .employeeId("emp124")
                .first_name("Jane")
                .last_name("Smith")
                .position("Developer")
                .email("jane.smith@example.com")
                .phone("9876543210")
                .salary(85000.00)
                .imagePath("/images/jane.png")
                .availability(List.of(
                        new Availability("Tuesday", "09:00", "17:00")
                ))
                .build();

        List<Employee> employees = List.of(employee1, employee2);

        // Perform Mapping
        List<EmployeeResponseModel> responses = mapper.entityListToResponseModel(employees);

        // Assertions
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("emp124", responses.get(1).getEmployeeId());
        assertEquals("Tuesday", responses.get(1).getAvailability().get(0).getDayOfWeek());
    }
}
