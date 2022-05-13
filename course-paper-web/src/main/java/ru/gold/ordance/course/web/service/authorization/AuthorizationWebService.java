package ru.gold.ordance.course.web.service.authorization;

import ru.gold.ordance.course.web.api.authorization.*;

public interface AuthorizationWebService {
    AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq);
    AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq);
    AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq);
}
