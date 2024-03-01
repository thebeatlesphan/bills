package com.example.bills.security;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest LoginRequest) {
        // Create an empty SecurityContext -> avoids race conditions from
        // SecurityContextHolder.getContext().setAuthentication(authentication)
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Create an Authentication object
        Authentication authenticationRequest = 
            new UsernamePasswordAuthenticationToken(LoginRequest.username(), passwordEncoder.encode(LoginRequest.password()));
        
        // Authenticate user
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        // Set the authenticated user in the context
        context.setAuthentication(authenticationResponse);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        System.out.println("teeeeeeeeeeeeeeeeeeeeeeeeeeeeest\n\n");
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(String username, String password) {
    }
}
