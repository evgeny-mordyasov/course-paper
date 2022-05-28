package ru.gold.ordance.course.web.service.authorization.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.web.service.authorization.config.JwtConfig;

import java.util.Date;

@Component
public class JwtProvider {
    private final UserDetailsService service;

    private final JwtConfig jwtConfig;

    public JwtProvider(UserDetailsService service, JwtConfig jwtConfig) {
        this.service = service;
        this.jwtConfig = jwtConfig;
    }

    public String createToken(String email) {
        Date currentDate = new Date();

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(email))
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + jwtConfig.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    public boolean tokenIsValid(String token) {
        try {
            return service.loadUserByUsername(
                    Jwts.parser()
                            .setSigningKey(jwtConfig.getSecret())
                            .parseClaimsJws(token)
                            .getBody()
                            .getSubject())
                    .isEnabled();
        } catch (Exception ignored) {
            return false;
        }
    }
}
