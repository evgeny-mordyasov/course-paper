package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.GetByIdRequest;
import ru.gold.ordance.course.web.api.GetRequest;
import ru.gold.ordance.course.web.api.client.ClientGetByEmailRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorEntityId;
import static ru.gold.ordance.course.web.validate.ValidateHelper.errorString;

public class GetValidate implements RequestValidate<GetRequest> {
    @Override
    public void validate(GetRequest rq) {
        if (rq instanceof GetByIdRequest) {
            validateRequest((GetByIdRequest) rq);
        } else if (rq instanceof ClientGetByEmailRequest) {
            validateRequest((ClientGetByEmailRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(GetByIdRequest rq) {
        errorEntityId(rq.getEntityId());
    }

    private void validateRequest(ClientGetByEmailRequest rq) {
        errorString(rq.getEmail(), "email");
    }
}
