package com.example.bills.response;

import java.util.Date;
import java.util.List;

import com.example.bills.clan.Clan;
import com.example.bills.user.User;

public class LoginResponse extends ApiResponse<User> {

    private String username;
    private String token;
    private List<Clan> clans;

    public LoginResponse(String message, User data, List<Clan> clans, Date timestamp, String username, String token) {
        super(message, data, timestamp);
        this.username = username;
        this.token = token;
        this.clans = clans;
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

    public List<Clan> getClans() {
        return clans;
    }

    public void setClans(List<Clan> clans) {
        this.clans = clans;
    }
}
