package com.example.highenddetailing.employeessubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeInviteRepository extends JpaRepository<EmployeeInvite, Long> {
    Optional<EmployeeInvite> findByInviteToken(String inviteToken);
}