package com.enigmacamp.latihanspring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.latihanspring.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long expiration;

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getId())
                .withClaim("role", user.getRole())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(expiration))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Map<String, String> verifyAndGetClaims(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);

        return Map.of(
                "userId", decodedJWT.getSubject(),
                "role", decodedJWT.getClaim("role").asString()
        );
    }
}
