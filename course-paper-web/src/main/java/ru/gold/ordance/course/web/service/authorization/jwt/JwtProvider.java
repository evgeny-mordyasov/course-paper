package ru.gold.ordance.course.web.service.authorization.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.common.exception.BannedException;
import ru.gold.ordance.course.common.exception.UnauthorizedException;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.config.properties.JwtProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtProvider {
    private final static String TOKEN_PREFIX = "Bearer_";

    private final UserDetailsService service;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager manager;

    public JwtProvider(UserDetailsService service, JwtProperties jwtProperties, AuthenticationManager manager) {
        this.service = service;
        this.jwtProperties = jwtProperties;
        this.manager = manager;
    }

    public String createToken(String email) {
        Date currentDate = new Date();

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(email))
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + jwtProperties.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
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
        UserDetails userDetails = service.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
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

    public void authenticate(AuthorizationSignInRequest rq) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword()));

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException();
        } catch (LockedException e) {
            throw new BannedException();
        }
    }
}
