package com.example.bills.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void addUsernameAndPassword(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashPassword(password));
        userRepository.save(newUser);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByName(String userName) {
        return userRepository.findByUsername(userName);
    }
}
