package com.example.bills.user;

import com.example.bills.exception.UsernameAlreadyExistsException;
import com.example.bills.response.ApiResponse;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  ResponseEntity<ApiResponse<?>> registerUser(@RequestBody User user) {
    try {
      // Check if the username already exists
      if (userService.userExists(user.getUsername()))
        throw new UsernameAlreadyExistsException("Username already exists");

      // Add the user
      userService.addUsernameAndPassword(user.getUsername(), user.getPassword());

      // Return a success response
      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(new ApiResponse<>("Registered successfully", null, new Date()));
    } catch (UsernameAlreadyExistsException ex) {
      // Return an error response for the customer exception
      return ResponseEntity
          .badRequest()
          .body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (Exception ex) {
      // Return a generic error response for other exceptions
      return ResponseEntity
          .status(500)
          .body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }
}
