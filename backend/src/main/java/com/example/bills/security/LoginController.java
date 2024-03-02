package com.example.bills.security;

import com.example.bills.exception.ApiResponse;
import com.example.bills.jwt.JwtTokenProvider;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest LoginRequest) {
        try {
            // Create an empty SecurityContext
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

            return ResponseEntity.ok().body(new ApiResponse("Successfully logged in", token, new Date()));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ApiResponse(ex.getMessage(), null, new Date()));
        }
    }

    public record LoginRequest(String username, String password) {
    }
}
