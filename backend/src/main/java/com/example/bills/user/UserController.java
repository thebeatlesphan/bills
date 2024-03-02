package com.example.bills.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bills.exception.ApiResponse;
import com.example.bills.exception.UsernameAlreadyExistsException;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public Iterable<User> getAllusers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
        try {
            // Check if the username already exists
            if (userRepository.findByUsername(user.getUsername()) != null) {
                // If username exists, throw the custom exception
                throw new UsernameAlreadyExistsException("Username already exists");
            }
            
            // Hash the password using bcrypt
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            
            // If username doesn't exists, register the user
            userRepository.save(user);
            
            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered successfully",null, new Date()));
        } catch (UsernameAlreadyExistsException ex) {
            // Return an error response for the customer exception
            return ResponseEntity.badRequest()
                .body(new ApiResponse(ex.getMessage(), null, new Date()));
        } catch (Exception ex) {
            // Return a generic error response for other exceptions
            return ResponseEntity.status(500)
                .body(new ApiResponse(ex.getMessage(), null, new Date()));
        }
    }

    @PostMapping("/login")
    ResponseEntity<String> loginUser(@RequestBody User user) {
        // Check if the username exists
        if (userRepository.findByUsername(user.getUsername()) == null) {
            return ResponseEntity.badRequest().body("Username invalid");
        }

        // Check if the password matches the user's
        return ResponseEntity.accepted().body("Login successful");
    }
}