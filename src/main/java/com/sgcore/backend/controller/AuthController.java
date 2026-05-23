//package com.sgcore.backend.controller;
//
//import com.sgcore.backend.dto.LoginRequest;
//import com.sgcore.backend.service.AuthService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        return authService.login(request);
//    }
//
//    @GetMapping("/verify")
//    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String authHeader) {
//        return authService.verify(authHeader);
//    }
//}
package com.sgcore.backend.controller;

import com.sgcore.backend.dto.LoginRequest;
import com.sgcore.backend.dto.LoginResponse;
import com.sgcore.backend.model.Admin;
import com.sgcore.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request,
                               HttpServletRequest httpRequest) {

        Admin admin = authService.authenticate(
                request.getEmail(),
                request.getPassword()
        );

        if (admin == null) {
            return new LoginResponse(false, "Invalid credentials");
        }

        HttpSession session = httpRequest.getSession(true);

        session.setAttribute("ADMIN_ID", admin.getId());
        session.setAttribute("ADMIN_EMAIL", admin.getEmail());
        session.setAttribute("ROLE", "ADMIN");

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        admin.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        return new LoginResponse(true, "Login successful");
    }

    @GetMapping("/verify")
    public LoginResponse verify(HttpSession session) {

        Object role = session.getAttribute("ROLE");

        if (role == null) {
            return new LoginResponse(false, "Not authenticated");
        }

        return new LoginResponse(true, "Authenticated");
    }

    @PostMapping("/logout")
    public LoginResponse logout(HttpSession session) {

        session.invalidate();

        return new LoginResponse(true, "Logged out");
    }
}
