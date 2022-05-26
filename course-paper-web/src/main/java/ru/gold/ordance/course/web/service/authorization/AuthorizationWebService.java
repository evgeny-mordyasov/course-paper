package ru.gold.ordance.course.web.service.authorization;

import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.service.WebService;

public interface AuthorizationWebService extends WebService {
    AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq);
    AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq);
    AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq);
}
