package ru.gold.ordance.course.web.service.authorization.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.mapper.ClientMapper;

@Service
public class AuthorizationWebServiceImpl implements AuthorizationWebService {
    private final ClientService service;

    private final ClientMapper mapper;

    private final AuthenticationManager manager;

    private final JwtProvider provider;

    public AuthorizationWebServiceImpl(ClientService service,
                                       ClientMapper mapper,
                                       AuthenticationManager manager,
                                       JwtProvider provider) {
        this.service = service;
        this.mapper = mapper;
        this.manager = manager;
        this.provider = provider;
    }

    @Override
    public AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq) {
        Client savedClient = service.save(mapper.toClient(rq));
        String token = provider.createToken(savedClient.getEmail());

        return AuthorizationSignUpResponse.success(mapper.fromClient(savedClient), token);
    }

    @Override
    public AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword()));

        return AuthorizationSignInResponse.success(provider.createToken(rq.getEmail()));
    }

    @Override
    public AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq) {
        return AuthorizationTokenLifeResponse.success(provider.tokenIsValid(rq.getToken()));
    }
}
