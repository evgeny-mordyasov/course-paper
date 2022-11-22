package ru.gold.ordance.course.web.service.web.authorization.impl;

import org.springframework.security.authentication.*;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.ConfirmationToken;
import ru.gold.ordance.course.base.service.core.sub.ClientService;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.exception.BannedException;
import ru.gold.ordance.course.web.exception.UnauthorizedException;
import ru.gold.ordance.course.web.mapper.ClientMapper;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.web.authorization.EmailSenderWebService;
import ru.gold.ordance.course.web.service.web.authorization.jwt.JwtProvider;

public class AuthorizationWebServiceImpl implements AuthorizationWebService {
    private final ClientService clientService;
    private final ClientMapper mapper;
    private final AuthenticationManager manager;
    private final JwtProvider provider;
    private final EmailSenderWebService emailSenderService;

    public AuthorizationWebServiceImpl(ClientService clientService,
                                       ClientMapper mapper,
                                       AuthenticationManager manager,
                                       JwtProvider provider,
                                       EmailSenderWebService emailSenderService) {
        this.clientService = clientService;
        this.mapper = mapper;
        this.manager = manager;
        this.provider = provider;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    @Override
    public AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq) {
        Client savedClient = clientService.save(mapper.toClient(rq));
        emailSenderService.send(savedClient);

        return AuthorizationSignUpResponse.success(mapper.fromClient(savedClient));
    }

    @Override
    public AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword()));

            Client client = clientService.getByEmail(rq.getEmail());

            return AuthorizationSignInResponse.success(mapper.fromClient(client), provider.createToken(rq.getEmail()));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException();
        } catch (LockedException e) {
            throw new BannedException();
        }
    }

    @Override
    public AuthorizationConfirmAccountResponse confirmAccount(AuthorizationConfirmAccountRequest rq) {
        ConfirmationToken confirmationToken = emailSenderService.getByToken(rq.getToken());

        if (confirmationToken.isExpired()) {
            return AuthorizationConfirmAccountResponse.banned();
        }

        activateClient(confirmationToken);

        return AuthorizationConfirmAccountResponse.success();
    }

    private void activateClient(ConfirmationToken confirmationToken) {
        Client client = confirmationToken.getClient();
        client = client.toBuilder().withIsActive(true).build();
        clientService.update(client);
    }
}
