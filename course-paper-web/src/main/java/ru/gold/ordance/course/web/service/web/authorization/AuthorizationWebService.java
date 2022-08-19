package ru.gold.ordance.course.web.service.web.authorization;

import ru.gold.ordance.course.web.api.authorization.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface AuthorizationWebService extends WebService {
    AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq);
    AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq);
    AuthorizationTokenLifeResponse tokenLife(AuthorizationTokenLifeRequest rq);
}
