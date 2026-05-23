package com.sgcore.backend.service;

import com.sgcore.backend.model.Admin;
import com.sgcore.backend.model.PasswordResetToken;
import com.sgcore.backend.repository.AdminRepository;
import com.sgcore.backend.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final AdminRepository adminRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.url}")
    private String frontendUrl;

    public PasswordResetService(
            PasswordResetTokenRepository tokenRepository,
            AdminRepository adminRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder
    ) {
        this.tokenRepository = tokenRepository;
        this.adminRepository = adminRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendResetLink(String email) {

        Admin admin = adminRepository.findByEmail(email)
                .orElse(null);

        if (admin == null) {
            return;
        }

        tokenRepository.deleteByEmail(email);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(
                LocalDateTime.now().plusMinutes(15)
        );

        tokenRepository.save(resetToken);

        String base = frontendUrl.endsWith("/")
                ? frontendUrl.substring(0, frontendUrl.length() - 1)
                : frontendUrl;

        String resetLink =
                base + "/reset-password?token=" + token;

        emailService.sendResetEmail(email, resetLink);
    }

    public boolean resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository
                .findByToken(token)
                .orElse(null);

        if (resetToken == null) {
            return false;
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Admin admin = adminRepository
                .findByEmail(resetToken.getEmail())
                .orElse(null);

        if (admin == null) {
            return false;
        }

        admin.setPassword(
                passwordEncoder.encode(newPassword)
        );

        adminRepository.save(admin);

        tokenRepository.delete(resetToken);

        return true;
    }
}
