package ru.gold.ordance.course.web.service.web.authorization.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.web.service.web.authorization.config.JwtConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtProvider {
    private final static String TOKEN_PREFIX = "Bearer_";

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

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.service.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenIsValid(String token) {
        try {
            return service.loadUserByUsername(getUsername(token))
                    .isEnabled();
        } catch (Exception ignored) {
            return false;
        }
    }
}
