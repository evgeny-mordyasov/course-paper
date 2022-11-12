package ru.gold.ordance.course.web.service.web.authorization.impl;

import org.springframework.security.authentication.*;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.core.sub.ClientService;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.exception.BannedException;
import ru.gold.ordance.course.web.exception.UnauthorizedException;
import ru.gold.ordance.course.web.mapper.ClientMapper;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.web.authorization.jwt.JwtProvider;

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
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword()));

            Client client = service.getByEmail(rq.getEmail());

            return AuthorizationSignInResponse.success(mapper.fromClient(client), provider.createToken(rq.getEmail()));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException();
        } catch (LockedException e) {
            throw new BannedException();
        }
    }
}
