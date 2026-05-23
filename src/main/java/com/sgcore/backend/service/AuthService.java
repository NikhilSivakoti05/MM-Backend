//package com.sgcore.backend.service;
//
//import com.sgcore.backend.dto.LoginRequest;
//import com.sgcore.backend.model.Admin;
//import com.sgcore.backend.repository.AdminRepository;
//import com.sgcore.backend.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class AuthService {
//
//    private final AdminRepository repo;
//    private final PasswordEncoder encoder;
//    private final JwtUtil jwtUtil;
//
//    public AuthService(AdminRepository repo,
//                       PasswordEncoder encoder,
//                       JwtUtil jwtUtil) {
//        this.repo = repo;
//        this.encoder = encoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    public ResponseEntity<?> login(LoginRequest req) {
//
//        Admin admin = repo.findByEmail(req.getEmail())
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//
//        if (!encoder.matches(req.getPassword(), admin.getPassword())) {
//            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
//        }
//
//        String token = jwtUtil.generate(admin.getId());
//
//        return ResponseEntity.ok(Map.of(
//                "token", token,
//                "accessToken", token,
//                "email", admin.getEmail()
//        ));
//    }
//
//    public ResponseEntity<?> verify(String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(401).body(Map.of("valid", false, "error", "No token provided"));
//        }
//
//        try {
//            String token = authHeader.substring(7);
//            Claims claims = jwtUtil.parse(token);
//            Admin admin = repo.findById(claims.getSubject())
//                    .orElse(null);
//
//            if (admin == null) {
//                return ResponseEntity.status(401).body(Map.of("valid", false, "error", "Admin not found"));
//            }
//
//            return ResponseEntity.ok(Map.of(
//                    "valid", true,
//                    "email", admin.getEmail()
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body(Map.of("valid", false, "error", "Invalid or expired token"));
//        }
//    }
//}
package com.sgcore.backend.service;

import com.sgcore.backend.model.Admin;
import com.sgcore.backend.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AdminRepository adminRepository,
                       PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin authenticate(String email, String password) {

        Admin admin = adminRepository.findByEmail(email)
                .orElse(null);

        if (admin == null) {
            return null;
        }

        boolean matches = passwordEncoder.matches(
                password,
                admin.getPassword()
        );

        if (!matches) {
            return null;
        }

        return admin;
    }
}
