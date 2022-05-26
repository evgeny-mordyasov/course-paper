package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.rest.AuthorizationRestController;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;
import static ru.gold.ordance.course.web.utils.LoggerUtils.*;

@RestController
@RequestMapping("/api/v1/authorizations")
public class AuthorizationRestControllerImpl implements AuthorizationRestController {
    private final AuthorizationWebService service;

    public AuthorizationRestControllerImpl(AuthorizationWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/sign-up", consumes = JSON, produces = JSON)
    public Response signUp(@RequestBody AuthorizationSignUpRequest rq) {
        try {
            loggingMessage("Sign up request received: {}", rq);

            return service.signUp(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(value = "/sign-in", consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        try {
            loggingMessage("Sign in request received: {}", rq);

            return service.signIn(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(value = "/token", consumes = JSON, produces = JSON)
    public Response tokenLife(@RequestBody AuthorizationTokenLifeRequest rq) {
        try {
            loggingMessage("Token life request received: {}", rq);

            return service.tokenLife(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}
