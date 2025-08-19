package com.gamercein.backend.controller;

import com.gamercein.backend.model.User;
import com.gamercein.backend.repository.UserRepository;
import com.gamercein.backend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "https://e-commerce-frontend-q6t5.vercel.app"})
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register: stores user with HASHED password
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User body) {
        if (body.getEmail() == null || body.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required"));
        }
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already exists"));
        }

        User u = new User();
        u.setUsername(body.getUsername() != null ? body.getUsername() : body.getEmail());
        u.setEmail(body.getEmail());
        u.setRole(body.getRole() != null ? body.getRole() : "user");
        u.setPassword(passwordEncoder.encode(body.getPassword())); // hash!

        User saved = userRepository.save(u);

        // optional: return token on register
        String token = jwtUtil.generateToken(saved.getEmail());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", saved.getUsername(),
                "userId", saved.getId()
        ));
    }

    // Login: checks raw password against hashed, returns JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required"));
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "userId", user.getId()
        ));
    }
}
