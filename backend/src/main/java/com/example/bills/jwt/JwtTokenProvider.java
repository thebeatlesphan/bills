package com.example.bills.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.bills.user.UserPrincipal;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = getSigningKey().getEncoded();
        return Keys.hmacShaKeyFor((keyBytes));
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject((Long.toString(userPrincipal.getId())))
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Jws<Claims> getUsernameFromToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return claims;
        } catch (ExpiredJwtException ex) {
            // Token has expired
            System.out.println("JWT Token has expired");
            throw ex; // or handle accordingly
        } catch (MalformedJwtException ex) {
            // Token is malformed
            System.out.println("Malformed JWT Token");
            throw ex; // or handle accordingly
        } catch (SignatureException ex) {
            // Token signature is invalid
            System.out.println("Invalid JWT Token signature");
            throw ex; // or handle accordingly
        } catch (JwtException ex) {
            // Other generic JWT exception
            System.out.println("JWT Token exception: " + ex.getMessage());
            throw ex; // or handle accordingly
        }
    }
};