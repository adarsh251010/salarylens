package com.adarsh.salarylens.controller;

import com.adarsh.salarylens.dto.AuthResponseDTO;
import com.adarsh.salarylens.dto.RegisterRequestDTO;
import com.adarsh.salarylens.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.register(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody Map<String, String> credentials) {
        String token = authService.login(
                credentials.get("username"),
                credentials.get("password")).getToken();
        String username = credentials.get("username");
        return ResponseEntity.ok(new AuthResponseDTO(token, username));
    }
}