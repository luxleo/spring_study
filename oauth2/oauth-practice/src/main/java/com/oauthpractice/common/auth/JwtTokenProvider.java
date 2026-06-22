package com.oauthpractice.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Duration expiration;
    private final Key SECRET_KEY;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Duration expiration) {
        this.expiration = expiration;
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email, String role){
//        claims는 jwt토큰의 payload부분을 의미
        Claims claims = Jwts.claims().subject(email)
                .add("role", role)
                .build();
        Date now = new Date();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration.toMillis()))
                .signWith(SECRET_KEY)
                .compact();
    }
}
