package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.UpdateRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationUpdateRequest;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorEntityId;
import static ru.gold.ordance.course.web.validate.ValidateHelper.errorString;

public class UpdateValidate implements RequestValidate<UpdateRequest> {
    @Override
    public void validate(UpdateRequest rq) {
        if (rq instanceof ClientUpdateRequest) {
            validateRequest((ClientUpdateRequest) rq);
        } else if (rq instanceof ClassificationUpdateRequest) {
            validateRequest((ClassificationUpdateRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(ClientUpdateRequest rq) {
        errorEntityId(rq.getEntityId());
        errorString(rq.getSurname(), "surname");
        errorString(rq.getName(), "name");
        errorString(rq.getPatronymic(), "patronymic");
        errorString(rq.getPassword(), "password");
    }

    private void validateRequest(ClassificationUpdateRequest rq) {
        errorEntityId(rq.getEntityId());
        errorString(rq.getName(), "name");
    }
}
