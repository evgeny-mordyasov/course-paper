package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.authorization.*;

public interface AuthorizationRestController {
    Response signUp(AuthorizationSignUpRequest rq);
    Response signIn(AuthorizationSignInRequest rq);
}
