package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.DeleteByIdRequest;
import ru.gold.ordance.course.web.api.DeleteRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorEntityId;

public class DeleteValidate implements RequestValidate<DeleteRequest> {
    @Override
    public void validate(DeleteRequest rq) {
        if (rq instanceof DeleteByIdRequest) {
            validateRequest((DeleteByIdRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(DeleteByIdRequest rq) {
        errorEntityId(rq.getEntityId());
    }
}
