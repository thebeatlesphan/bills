package com.example.bills.response;

import com.example.bills.user.User;
import java.util.Date;

public class LoginResponse extends ApiResponse<User> {
  private Integer userId;
  private String username;
  private String token;

  public LoginResponse(
    String message,
    Integer userId,
    String username,
    String token,
    Date timestamp
  ) {
    super(message, null, timestamp);
    this.userId = userId;
    this.username = username;
    this.token = token;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
