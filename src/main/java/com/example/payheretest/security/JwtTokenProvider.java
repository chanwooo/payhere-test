package com.example.payheretest.security;

import com.example.payheretest.domain.entity.User;
import com.example.payheretest.exception.NoSuchUserException;
import com.example.payheretest.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key encodedSecretKey;
    private final long aliveDurationMilli;
    private final UserRepository userRepository;

    public JwtTokenProvider(@Value("${security.jwt.token.secretKey}") final String secretKey,
                            @Value("${security.jwt.token.aliveDurationMilli}") final long aliveDurationMilli,
                            final UserRepository userRepository
    ) {
        this.encodedSecretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
        this.aliveDurationMilli = aliveDurationMilli;
        this.userRepository = userRepository;
    }

    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + aliveDurationMilli);
        userRepository.findByEmail(userId)
                .orElseThrow(NoSuchUserException::new)
                .setExpiredAt(expiredAt.getTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(encodedSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUserId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(encodedSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(encodedSecretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claims.getBody();

            User user = userRepository.findByEmail(body.getSubject()).orElseThrow(NoSuchUserException::new);

            if (body.getExpiration().before(new Date())) {
                return false;
            }

            if (new Date(user.getExpiredAt()).before(new Date())) {
                return false;
            }

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserId(HttpServletRequest request) {
        String token = resolveToken(request);
        return getUserId(token);
    }

}
