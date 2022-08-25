package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationTokenLifeRequest;
import ru.gold.ordance.course.web.rest.AuthorizationRestController;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

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
        return execute(() -> service.signUp(rq));
    }

    @Override
    @PostMapping(value = "/sign-in", consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        return execute(() -> service.signIn(rq));
    }

    @Override
    @PostMapping(value = "/token", consumes = JSON, produces = JSON)
    public Response tokenLife(@RequestBody AuthorizationTokenLifeRequest rq) {
        return execute(() -> service.tokenLife(rq));
    }
}
