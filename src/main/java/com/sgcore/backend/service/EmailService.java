package com.sgcore.backend.service;

import com.sgcore.backend.config.BrevoConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailService {

    private final BrevoConfig brevoConfig;

    public EmailService(BrevoConfig brevoConfig) {
        this.brevoConfig = brevoConfig;
    }

    public void sendResetEmail(String toEmail, String resetLink) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoConfig.getApiKey());

        Map<String, Object> body = Map.of(
                "sender", Map.of(
                        "name", "SG Core",
                        "email", "your-email@example.com"
                ),
                "to", new Object[]{
                        Map.of("email", toEmail)
                },
                "subject", "Reset Your Password",
                "htmlContent",
                "<h2>Password Reset</h2>" +
                "<p>Click below to reset your password:</p>" +
                "<a href='" + resetLink + "'>Reset Password</a>"
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        restTemplate.exchange(
                "https://api.brevo.com/v3/smtp/email",
                HttpMethod.POST,
                entity,
                String.class
        );
    }
}
