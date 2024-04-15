package com.example.bills.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String getJwtUserId(String token) {
        String _token = token.substring(7);
        return jwtTokenProvider
                .getUsernameFromToken(_token)
                .getPayload()
                .getSubject();
    }
}