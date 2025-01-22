package com.example.highenddetailing.employeesubdomain.businesslayer;

import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeServiceImpl;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.AvailabilityResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        // Arrange: create two Employee entities
        Employee empEntity1 = Employee.builder()
                .employeeId("e1f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("1321232")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        Employee empEntity2 = Employee.builder()
                .employeeId("e2f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("121212")
                .salary(55000.00)
                .imagePath("profile.png")
                .build();

        List<Employee> entities = List.of(empEntity1, empEntity2);

        // Create corresponding response models
        EmployeeResponseModel resp1 = EmployeeResponseModel.builder()
                .employeeId("e1f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("1321232")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        EmployeeResponseModel resp2 = EmployeeResponseModel.builder()
                .employeeId("e2f14c90-ec5e-4f82-a9b7-2548a7325b34")
                .first_name("John")
                .last_name("Doe")
                .position("Technician")
                .email("john.doe@example.com")
                .phone("121212")
                .salary(55000.00)
                .imagePath("profile.png")
                .build();

        List<EmployeeResponseModel> responseModels = List.of(resp1, resp2);

        // Mock repo + mapper
        when(employeeRepository.findAll()).thenReturn(entities);
        when(employeeResponseMapper.entityListToResponseModel(entities)).thenReturn(responseModels);

        // Act
        List<EmployeeResponseModel> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(responseModels.size(), result.size());
        assertEquals("Jane", result.get(0).getFirst_name());
        assertEquals("John", result.get(1).getFirst_name());
    }

    @Test
    void whenGetEmployeeById_thenReturnEmployeeResponse() {
        // Arrange
        String employeeId = "e1f14c90-ec5e-4f82-a9b7-2548a7325b34";

        // Create an Employee entity
        Employee empEntity = Employee.builder()
                .employeeId(employeeId)
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        // Create a corresponding ResponseModel
        EmployeeResponseModel responseModel = EmployeeResponseModel.builder()
                .employeeId(employeeId)
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .phone("232323")
                .salary(75000.00)
                .imagePath("profile.png")
                .build();

        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(empEntity));
        when(employeeResponseMapper.entityToResponseModel(empEntity)).thenReturn(responseModel);

        // Act
        Optional<EmployeeResponseModel> result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(responseModel, result.get());
    }

    @Test
    void whenGetAvailabilityForEmployee_thenReturnList() {
        // Arrange
        String employeeId = "e1f14c90-ec5e-4f82-a9b7-2548a7325b34";
        Employee empEntity = Employee.builder()
                .employeeId(employeeId)
                .availability(List.of(
                        new Availability("Monday", "08:00", "12:00"),
                        new Availability("Wednesday", "09:00", "17:00")
                ))
                .build();

        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(empEntity));

        // Act
        var availabilityList = employeeService.getAvailabilityForEmployee(employeeId);

        // Assert
        assertEquals(2, availabilityList.size());
        assertEquals("Monday", availabilityList.get(0).getDayOfWeek());
        assertEquals("08:00", availabilityList.get(0).getStartTime());
        assertEquals("12:00", availabilityList.get(0).getEndTime());
    }
}
