package com.example.highenddetailing.employeessubdomain.datalayer;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
public class EmployeeIdentifier {
    private String employeeId;

    // Modified constructor to accept auth0UserId directly
    public EmployeeIdentifier(String auth0UserId) {
        this.employeeId = auth0UserId;
    }

    public EmployeeIdentifier() {
        // Default constructor for JPA
    }
}