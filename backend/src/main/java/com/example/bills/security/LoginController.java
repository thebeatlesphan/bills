package com.example.bills.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest LoginRequest) {
        Authentication authenticationRequest = 
            UsernamePasswordAuthenticationToken.unauthenticated(LoginRequest.username(), LoginRequest.password());
        Authentication authenticationResponse =
            this.authenticationManager.authenticate(authenticationRequest);
    }

    public record LoginRequest(String username, String password) {
    }
}
