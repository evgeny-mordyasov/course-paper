package ru.gold.ordance.course.web.service.authorization.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
public class AuthorizationWebServiceImpl implements AuthorizationWebService {

    private final ClientService service;

    private final AuthenticationManager manager;

    private final JwtTokenProvider provider;

    public AuthorizationWebServiceImpl(ClientService service, AuthenticationManager manager, JwtTokenProvider provider) {
        this.service = service;
        this.manager = manager;
        this.provider = provider;
    }

    @Override
    public AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq) {
        service.save(Client.builder()
                .withSurname(rq.getSurname())
                .withName(rq.getName())
                .withPatronymic(rq.getPatronymic())
                .withEmail(rq.getEmail())
                .withPassword(rq.getPassword())
                .build());

        return AuthorizationSignUpResponse.success();
    }

    @Override
    public AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq) {
        String email = rq.getEmail();

        manager.authenticate(new UsernamePasswordAuthenticationToken(email, rq.getPassword()));

        Optional<Client> user = service.findByEmail(email);

        String token = provider.createJwt(email, user.get().getRole().name());

        return AuthorizationSignInResponse.success(token, user.get().getRole().name());
    }

    @Override
    public AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq) {
        boolean isValid = provider.validateJwtToken(rq.getToken());

        return AuthorizationTokenLifeResponse.success(isValid);
    }
}
