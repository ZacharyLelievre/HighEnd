package com.example.highenddetailing.Webhook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping("/restart")
    public ResponseEntity<String> restartContainer(
            @RequestBody Map<String, String> request) {

        System.out.println("Skipping signature verification for now.");

        String containerName = request.get("container");
        if (containerName == null || containerName.isEmpty()) {
            return ResponseEntity.badRequest().body("No container specified");
        }

        try {
            Process process = Runtime.getRuntime().exec("docker restart " + containerName);
            process.waitFor();
            return ResponseEntity.ok("Restarted container: " + containerName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to restart " + containerName);
        }
    }
}
