package com.example.payheretest.security;

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
                            final UserRepository userRepository) {
        this.encodedSecretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
        this.aliveDurationMilli = aliveDurationMilli;
        this.userRepository = userRepository;
        log.info("secretKey = " + secretKey);
        log.info("aliveDurationMilli = " + aliveDurationMilli);
    }

    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        log.info("now = " + now);
        Date expiredAt = new Date(now.getTime() + aliveDurationMilli);
        log.info("expiredAt = " + expiredAt);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(encodedSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        log.info("bearerToken = " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUserId(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(encodedSecretKey).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(encodedSecretKey).build().parseClaimsJws(token);
            Claims body = claims.getBody();
            if (body.getExpiration().before(new Date())) {
                return false;
            }
            userRepository.findByEmail(body.getSubject()).orElseThrow(NoSuchUserException::new);
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