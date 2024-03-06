package com.example.bills.security;

import com.example.bills.clan.Clan;
import com.example.bills.clan.ClanRepository;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;
import com.example.bills.response.LoginResponse;
import com.example.bills.user.User;
import com.example.bills.user.UserRepository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final ClanRepository clanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            ClanRepository clanRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.clanRepository = clanRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest LoginRequest) {
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

            // Retrieve User details
            Integer userId = userRepository.findByUsername(LoginRequest.username()).getId();

            // // Retrieve Clan details
            List<Clan> clans = clanRepository.findByOwnerId(userId).stream().toList();
            
            // Create a LoginResponse object with username and token
            LoginResponse loginResponse = new LoginResponse(
                    "Login successful",
                    null,
                    clans,
                    new Date(),
                    LoginRequest.username(),
                    token);

            return ResponseEntity.ok(loginResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ApiResponse<>(ex.getMessage(), null, new Date()));
        }
    }

    public record LoginRequest(String username, String password) {
    }
}
