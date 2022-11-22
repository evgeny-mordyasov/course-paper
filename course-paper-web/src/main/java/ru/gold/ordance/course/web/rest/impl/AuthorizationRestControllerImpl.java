package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.authorization.AuthorizationConfirmAccountRequest;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.rest.AuthorizationRestController;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Authorization.BASE_URL)
public class AuthorizationRestControllerImpl implements AuthorizationRestController {
    private final AuthorizationWebService service;

    public AuthorizationRestControllerImpl(AuthorizationWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = Endpoint.Authorization.SIGN_UP, consumes = JSON, produces = JSON)
    public Response signUp(@RequestBody AuthorizationSignUpRequest rq) {
        return execute(() -> service.signUp(rq));
    }

    @Override
    @PostMapping(value = Endpoint.Authorization.SIGN_IN, consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        return execute(() -> service.signIn(rq));
    }

    @Override
    @GetMapping(value = Endpoint.Authorization.CONFIRM_ACCOUNT, params = "token", produces = JSON)
    public Response confirmAccount(@RequestParam("token") String token) {
        return execute(() -> service.confirmAccount(new AuthorizationConfirmAccountRequest(token)));
    }
}
