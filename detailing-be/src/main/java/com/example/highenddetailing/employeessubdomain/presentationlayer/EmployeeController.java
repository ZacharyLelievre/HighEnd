package com.example.highenddetailing.employeessubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeServiceImpl;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmployeeResponseModel>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @GetMapping(value = "/{employeeId}", produces = "application/json")
    public ResponseEntity<EmployeeResponseModel> getEmployeeById(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{employeeId}/availability", produces = "application/json")
    public ResponseEntity<List<Availability>> getAvailability(@PathVariable String employeeId, @RequestHeader("Authorization") String authorization) {
        System.out.println("Authorization Header: " + authorization);
        return ResponseEntity.ok(employeeService.getAvailabilityByEmployeeId(employeeId));
    }


}
