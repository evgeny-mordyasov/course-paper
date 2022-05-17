package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.rest.AuthorizationRestController;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.validate.Validator;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/authorizations")
public class AuthorizationRestControllerImpl implements AuthorizationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRestControllerImpl.class);

    private final AuthorizationWebService service;

    private Validator validator;

    public AuthorizationRestControllerImpl(AuthorizationWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/sign-up", consumes = JSON, produces = JSON)
    public AuthorizationSignUpResponse signUp(@RequestBody AuthorizationSignUpRequest rq) {
        try {
            LOGGER.info("Sign up request received: {}", rq);

            validate(rq);
            AuthorizationSignUpResponse rs = service.signUp(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthorizationSignUpResponse rs = AuthorizationSignUpResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @PostMapping(value = "/sign-in", consumes = JSON, produces = JSON)
    public AuthorizationSignInResponse signIn(@RequestBody AuthorizationSignInRequest rq) {
        try {
            LOGGER.info("Sign in request received: {}", rq);

            validate(rq);
            AuthorizationSignInResponse rs = service.signIn(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthorizationSignInResponse rs = AuthorizationSignInResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @PostMapping(value = "/token", consumes = JSON, produces = JSON)
    public AuthorizationTokenLifeResponse tokenLife(@RequestBody AuthorizationTokenLifeRequest rq) {
        try {
            LOGGER.info("Token life request received: {}", rq);

            validate(rq);
            AuthorizationTokenLifeResponse rs = service.tokenLife(rq);
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthorizationTokenLifeResponse rs = AuthorizationTokenLifeResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    private void validate(Request rq) {
        validator.validate(rq);
    }
}
