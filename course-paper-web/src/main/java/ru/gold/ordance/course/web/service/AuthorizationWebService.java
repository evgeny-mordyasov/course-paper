package ru.gold.ordance.course.web.service;

import org.springframework.security.authentication.*;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.ClientService;
import ru.gold.ordance.course.common.exception.BannedException;
import ru.gold.ordance.course.common.exception.UnauthorizedException;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationConfirmAccountRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignUpRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationConfirmAccountResponse;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationSignInResponse;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationSignUpResponse;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;
import ru.gold.ordance.course.web.mapper.ClientMapper;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtProvider;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

public class AuthorizationWebService implements WebService {
    private final ClientService clientService;
    private final ClientMapper mapper;
    private final JwtProvider provider;
    private final EmailSenderWebService emailSenderService;
    private final AuthenticationManager authenticationManager;

    public AuthorizationWebService(ClientService clientService,
                                   JwtProvider provider,
                                   EmailSenderWebService emailSenderService,
                                   AuthenticationManager authenticationManager) {
        this.clientService = clientService;
        this.mapper = ClientMapper.instance();
        this.provider = provider;
        this.emailSenderService = emailSenderService;
        this.authenticationManager = authenticationManager;
    }

    public AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq) {
        Client savedClient = clientService.save(mapper.toClient(rq));
        emailSenderService.send(savedClient);

        return AuthorizationSignUpResponse.success(mapper.fromClient(savedClient));
    }

    public AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq) {
        authenticate(rq);
        Client client = getEntity(clientService.findByEmail(rq.getEmail()));

        return AuthorizationSignInResponse.success(mapper.fromClient(client), provider.createToken(rq.getEmail()));
    }

    private void authenticate(AuthorizationSignInRequest rq) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(rq.getEmail(), rq.getPassword()));

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException();
        } catch (LockedException e) {
            throw new BannedException();
        }
    }

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
