package com.example.highenddetailing.employeessubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeServiceImpl;
import com.example.highenddetailing.employeessubdomain.datalayer.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmployeeResponseModel> createEmployee(@RequestBody EmployeeRequestModel request) {
        EmployeeResponseModel createdEmployee = employeeService.createEmployee(request);
        return ResponseEntity.status(201).body(createdEmployee);
    }
    @GetMapping("/me")
    public ResponseEntity<EmployeeResponseModel> getCurrentEmployee(@AuthenticationPrincipal Jwt jwt) {
        String auth0UserId = jwt.getSubject();
        EmployeeResponseModel employee = employeeService.getCurrentEmployee(auth0UserId);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
