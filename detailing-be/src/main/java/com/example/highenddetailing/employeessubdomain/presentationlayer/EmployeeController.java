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

    @GetMapping("/{employeeId}/availability")
    public ResponseEntity<List<AvailabilityResponseModel>> getEmployeeAvailability(@PathVariable String employeeId) {
        List<AvailabilityResponseModel> availability = employeeService.getAvailabilityForEmployee(employeeId);
        return ResponseEntity.ok(availability);
    }

    @PutMapping(value = "/{employeeId}/availability", consumes = "application/json")
    public ResponseEntity<Void> setEmployeeAvailability(
            @PathVariable String employeeId,
            @RequestBody List<Availability> newAvailability) {

        employeeService.setAvailabilityForEmployee(employeeId, newAvailability);
        return ResponseEntity.ok().build();
    }
    @PostMapping(produces = "application/json")
    public ResponseEntity<EmployeeResponseModel> createEmployee(@RequestBody EmployeeRequestModel employeeRequestModel) {
        // 1. Extract Auth0 sub from the request
        String auth0UserId = employeeRequestModel.getAuth0Sub();
        if (auth0UserId == null || auth0UserId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Call service to create/return existing
        try {
            EmployeeResponseModel response =
                    employeeService.createEmployee(employeeRequestModel, auth0UserId);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(500).build();
        }
    }


}
