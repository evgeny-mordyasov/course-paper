package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.internal.api.request.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.internal.api.request.authorization.AuthorizationSignUpRequest;

public interface AuthorizationRestController {
    Response signUp(AuthorizationSignUpRequest rq);
    Response signIn(AuthorizationSignInRequest rq);
    Response confirmAccount(String token);
}
