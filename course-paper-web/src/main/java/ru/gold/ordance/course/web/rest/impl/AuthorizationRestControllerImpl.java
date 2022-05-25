package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.rest.AuthorizationRestController;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/authorizations")
public class AuthorizationRestControllerImpl implements AuthorizationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRestControllerImpl.class);

    private final AuthorizationWebService service;

    public AuthorizationRestControllerImpl(AuthorizationWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/sign-up", consumes = JSON, produces = JSON)
    public Response signUp(@RequestBody AuthorizationSignUpRequest rq) {
        try {
            LOGGER.info("Sign up request received: {}", rq);

            rq.validate();
            Response rs = service.signUp(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(value = "/sign-in", consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        try {
            LOGGER.info("Sign in request received: {}", rq);

            rq.validate();
            Response rs = service.signIn(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(value = "/token", consumes = JSON, produces = JSON)
    public Response tokenLife(@RequestBody AuthorizationTokenLifeRequest rq) {
        try {
            LOGGER.info("Token life request received: {}", rq);

            rq.validate();
            Response rs = service.tokenLife(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }
}
