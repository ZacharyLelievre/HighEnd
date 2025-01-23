package com.example.highenddetailing.authsubdomain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth0")
public class Auth0ManagementController {

    @Value("${AUTH0_DOMAIN}")
    private String auth0Domain;

    @Value("${AUTH0_CLIENT_ID}")
    private String auth0ClientId;

    @Value("${AUTH0_CLIENT_SECRET}")
    private String auth0ClientSecret;

    // 1) Get Management API token
    private String getManagementApiToken() {
        String tokenUrl = "https://" + auth0Domain + "/oauth/token";

        Map<String, String> body = new HashMap<>();
        body.put("client_id", auth0ClientId);
        body.put("client_secret", auth0ClientSecret);
        body.put("audience", "https://" + auth0Domain + "/api/v2/");
        body.put("grant_type", "client_credentials");

        // e.g. use RestTemplate or WebClient to POST
        // parse the "access_token" out of the JSON response

        // PSEUDO:
        // String managementToken = ...
        return "MANAGEMENT_API_TOKEN";
    }

    // 2) Assign (or remove) a role
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(
            @RequestBody AssignRoleRequest request) {
        try {
            // request contains: auth0UserId, roleId
            String managementToken = getManagementApiToken();

            // Now call Auth0:
            String assignRoleUrl = "https://" + auth0Domain
                    + "/api/v2/users/" + request.getAuth0UserId() + "/roles";

            // JSON body for roles must look like: { "roles": ["<roleId>"] }
            // Each role in Auth0 has a numeric ID or GUID-like ID.

            // PSEUDO CODE to do the POST call using `managementToken`
            // Set Authorization: Bearer <managementToken>
            // Then pass { "roles": [request.getRoleId()] }

            // If success:
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to assign role: " + e.getMessage());
        }
    }
}
