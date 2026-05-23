package com.sgcore.backend.controller;

import com.sgcore.backend.dto.ForgotPasswordRequest;
import com.sgcore.backend.dto.LoginResponse;
import com.sgcore.backend.dto.ResetPasswordRequest;
import com.sgcore.backend.service.PasswordResetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(
            PasswordResetService passwordResetService
    ) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public LoginResponse forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {

        passwordResetService.sendResetLink(request.getEmail());

        return new LoginResponse(
                true,
                "If the email exists, reset link sent"
        );
    }

    @PostMapping("/reset-password")
    public LoginResponse resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {

        boolean success = passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );

        if (!success) {
            return new LoginResponse(false, "Invalid or expired token");
        }

        return new LoginResponse(true, "Password updated successfully");
    }
}
