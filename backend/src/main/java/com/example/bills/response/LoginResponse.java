package com.example.bills.response;

import java.util.Date;

import com.example.bills.user.User;

public class LoginResponse extends ApiResponse<User> {

    private String username;
    private String token;

    public LoginResponse(String message, User data, Date timestamp, String username, String token) {
        super(message, data, timestamp);
        this.username = username;
        this.token = token;
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
