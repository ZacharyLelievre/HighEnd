package com.example.highenddetailing.employeessubdomain.presentationlayer;

import com.example.highenddetailing.employeessubdomain.businesslayer.EmployeeInviteService;
import com.example.highenddetailing.employeessubdomain.datalayer.InviteResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/employee-invites")
public class EmployeeInviteController {

    private final EmployeeInviteService inviteService;

    public EmployeeInviteController(EmployeeInviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping
    public InviteResponse createInvite(@RequestBody Map<String, String> request) {
        String inviteLink = inviteService.generateInviteLink();
        return new InviteResponse(inviteLink);
    }

    @GetMapping("/{token}")
    public boolean validateInvite(@PathVariable String token) {
        return inviteService.isInviteValid(token);
    }
}
