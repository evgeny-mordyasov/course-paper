package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.*;
import ru.gold.ordance.course.web.api.authorization.AuthorizationRequest;

public final class Validate {
    private final static GetValidate GET = new GetValidate();
    private final static DeleteValidate DELETE = new DeleteValidate();
    private final static SaveValidate SAVE = new SaveValidate();
    private final static UpdateValidate UPDATE = new UpdateValidate();
    private final static AuthorizationValidate AUTH = new AuthorizationValidate();

    public static void validate(Request rq) {
        if (rq instanceof GetRequest) {
            GET.validate((GetRequest) rq);
        } else if (rq instanceof DeleteRequest) {
            DELETE.validate((DeleteRequest) rq);
        } else if (rq instanceof SaveRequest) {
            SAVE.validate((SaveRequest) rq);
        } else if (rq instanceof UpdateRequest) {
            UPDATE.validate((UpdateRequest) rq);
        } else if (rq instanceof AuthorizationRequest) {
            AUTH.validate((AuthorizationRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }
}
