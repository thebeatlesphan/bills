package com.example.bills.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public Iterable<User> getAllusers() {
        return userRepository.findAll();
    }

    @PostMapping("register")
    ResponseEntity<String> registerUser(@RequestBody User newUser) {
        // Check if the username already exists
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            // If username exists, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        // If username doesn't exists, register the user
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
