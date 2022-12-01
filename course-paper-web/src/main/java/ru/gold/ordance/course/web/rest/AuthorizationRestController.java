package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignUpRequest;

public interface AuthorizationRestController {
    Response signUp(AuthorizationSignUpRequest rq);
    Response signIn(AuthorizationSignInRequest rq);
    Response confirmAccount(String token);
}
