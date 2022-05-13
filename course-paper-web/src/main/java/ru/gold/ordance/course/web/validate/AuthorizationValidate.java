package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.authorization.AuthorizationRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationTokenLifeRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorString;

public class AuthorizationValidate implements RequestValidate<AuthorizationRequest> {
    @Override
    public void validate(AuthorizationRequest rq) {
        if (rq instanceof AuthorizationSignUpRequest) {
            validateRequest((AuthorizationSignUpRequest) rq);
        } else if (rq instanceof AuthorizationSignInRequest) {
            validateRequest((AuthorizationSignInRequest) rq);
        } else if (rq instanceof AuthorizationTokenLifeRequest) {
            validateRequest((AuthorizationTokenLifeRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(AuthorizationSignUpRequest rq) {
        errorString(rq.getSurname(), "surname");
        errorString(rq.getName(), "name");
        errorString(rq.getPatronymic(), "patronymic");
        errorString(rq.getEmail(), "email");
        errorString(rq.getPassword(), "password");
    }

    private void validateRequest(AuthorizationSignInRequest rq) {
        errorString(rq.getEmail(), "email");
        errorString(rq.getPassword(), "password");
    }

    private void validateRequest(AuthorizationTokenLifeRequest rq) {
        errorString(rq.getToken(), "token");
    }
}
