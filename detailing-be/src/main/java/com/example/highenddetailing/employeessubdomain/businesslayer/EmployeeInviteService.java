package com.example.highenddetailing.employeessubdomain.businesslayer;

public interface EmployeeInviteService {
    String generateInviteLink();
    boolean isInviteValid(String token);
}