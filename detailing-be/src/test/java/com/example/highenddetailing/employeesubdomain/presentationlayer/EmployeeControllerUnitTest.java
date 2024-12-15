package com.example.highenddetailing.employeesubdomain.presentationlayer;


import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeServiceImpl;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeController;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    private List<EmployeeResponseModel> employeeResponseModels;

    private EmployeeResponseModel employee1;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        employeeResponseModels = List.of(
               employee1 = EmployeeResponseModel.builder()
                        .employeeId("E001")
                        .first_name("Jane")
                        .last_name("Smith")
                        .position("Manager")
                        .email("jane.smith@example.com")
                        .salary(75000.00)
                        .imagePath("/images/employee1.jpg")
                        .build(),
                EmployeeResponseModel.builder()
                        .employeeId("E002")
                        .first_name("John")
                        .last_name("Doe")
                        .position("Technician")
                        .email("john.doe@example.com")
                        .salary(55000.00)
                        .imagePath("/images/employee2.jpg")
                        .build()
        );
    }

    @Test
    void testGetAllEmployees_EmptyList() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(List.of());


        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetEmployeeById_Success() throws Exception {
        // Arrange
        when(employeeService.getEmployeeById("E001")).thenReturn(Optional.of(employee1));

        // Act & Assert
        mockMvc.perform(get("/api/employees/E001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("E001"))
                .andExpect(jsonPath("$.first_name").value("Jane"))
                .andExpect(jsonPath("$.last_name").value("Smith"))
                .andExpect(jsonPath("$.position").value("Manager"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.salary").value(75000.00))
                .andExpect(jsonPath("$.imagePath").value("/images/employee1.jpg"));
    }

}