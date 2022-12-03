package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationConfirmAccountRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.service.AuthorizationWebService;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Authorization.BASE_URL)
public class AuthorizationRestController {
    private final AuthorizationWebService service;

    public AuthorizationRestController(AuthorizationWebService service) {
        this.service = service;
    }

    @PostMapping(value = Endpoint.Authorization.SIGN_UP, consumes = JSON, produces = JSON)
    public Response signUp(@RequestBody AuthorizationSignUpRequest rq) {
        return execute(() -> service.signUp(rq));
    }

    @PostMapping(value = Endpoint.Authorization.SIGN_IN, consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        return execute(() -> service.signIn(rq));
    }

    @GetMapping(value = Endpoint.Authorization.CONFIRM_ACCOUNT, params = "token", produces = JSON)
    public Response confirmAccount(@RequestParam("token") String token) {
        return execute(() -> service.confirmAccount(new AuthorizationConfirmAccountRequest(token)));
    }
}
