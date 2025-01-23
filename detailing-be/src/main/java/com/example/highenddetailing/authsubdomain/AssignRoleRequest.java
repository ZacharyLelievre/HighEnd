package com.example.highenddetailing.authsubdomain;

import lombok.Data;

@Data
public class AssignRoleRequest {
    private String auth0UserId;  // e.g. "auth0|123abc..."
    private String roleId;       // e.g. "rol_Asdf1234..."
}
