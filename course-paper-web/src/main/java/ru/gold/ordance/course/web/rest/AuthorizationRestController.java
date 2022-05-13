package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.authorization.*;

public interface AuthorizationRestController {
    AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq);
    AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq);
    AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq);
}
