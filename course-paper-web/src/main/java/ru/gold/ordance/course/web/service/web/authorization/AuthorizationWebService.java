package ru.gold.ordance.course.web.service.web.authorization;

import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationConfirmAccountRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignUpRequest;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationConfirmAccountResponse;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationSignInResponse;
import ru.gold.ordance.course.internal.api.domain.authorization.response.AuthorizationSignUpResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface AuthorizationWebService extends WebService {
    AuthorizationSignUpResponse signUp(AuthorizationSignUpRequest rq);
    AuthorizationSignInResponse signIn(AuthorizationSignInRequest rq);
    AuthorizationConfirmAccountResponse confirmAccount(AuthorizationConfirmAccountRequest rq);
}
