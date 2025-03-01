package com.example.highenddetailing.Webhook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String GITHUB_SECRET = "123"; // Same as GitHub webhook secret

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestBody String payload) {

        if (!verifySignature(signature, payload, GITHUB_SECRET)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
        }

        // Process webhook data (log or trigger actions)
        System.out.println("Webhook received: " + payload);

        return ResponseEntity.ok("Webhook received");
    }

    private boolean verifySignature(String signature, String payload, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
            String computedHash = "sha256=" + Hex.encodeHexString(mac.doFinal(payload.getBytes()));

            return computedHash.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
