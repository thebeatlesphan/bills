package com.example.bills.security;
import com.example.bills.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest LoginRequest) {
        // Create an empty SecurityContext -> avoids race conditions from
        // SecurityContextHolder.getContext().setAuthentication(authentication)
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Create an Authentication object
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(
                LoginRequest.username(),
                LoginRequest.password());

        // Authenticate user
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        // Set the authenticated user in the context
        context.setAuthentication(authenticationResponse);

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(authenticationResponse);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        System.out.println("teeeeeeeeeeeeeeeeeeeeeeeeeeeeest\n\n");

        Map<String, String> response = new HashMap<>();
        response.put("message", "test successful");

        return ResponseEntity.ok(response);
    }

    public record LoginRequest(String username, String password) {
    }
}
