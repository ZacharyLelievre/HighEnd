package com.example.highenddetailing.employeessubdomain.datalayer;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
@EqualsAndHashCode
public class EmployeeIdentifier {
    private String employeeId;

    public EmployeeIdentifier() {
        this.employeeId= UUID.randomUUID().toString();
    }
    public EmployeeIdentifier(String employeeId) {
        this.employeeId = employeeId;
    }
}