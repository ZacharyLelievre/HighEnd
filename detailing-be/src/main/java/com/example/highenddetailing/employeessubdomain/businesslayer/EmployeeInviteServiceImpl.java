package com.example.highenddetailing.employeessubdomain.businesslayer;

import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeInvite;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeInviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeInviteServiceImpl implements EmployeeInviteService {
    private final EmployeeInviteRepository inviteRepo;

    @Override
    public String generateInviteLink() {
        // Generate a random token
        String token = UUID.randomUUID().toString();

        // expires in 24 hours
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(1);

        EmployeeInvite invite = EmployeeInvite.builder()
                .inviteToken(token)
                .expiresAt(expiresAt)
                .build();
        inviteRepo.save(invite);

        return "https://high-end-detailing.com/employee-invite/" + token;
    }

    @Override
    public boolean isInviteValid(String token) {
        return inviteRepo.findByInviteToken(token)
                .filter(inv -> inv.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}