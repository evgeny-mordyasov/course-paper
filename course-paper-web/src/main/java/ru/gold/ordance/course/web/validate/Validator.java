package ru.gold.ordance.course.web.validate;

import org.springframework.stereotype.Component;
import ru.gold.ordance.course.web.api.*;
import ru.gold.ordance.course.web.api.authorization.AuthorizationRequest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Validator {
    private final GetValidate GET;
    private final SaveValidate SAVE;
    private final UpdateValidate UPDATE;
    private final DeleteValidate DELETE;
    private final AuthorizationValidate AUTH;

    public Validator(GetValidate get,
                     SaveValidate save,
                     UpdateValidate update,
                     DeleteValidate delete,
                     AuthorizationValidate auth) {
        this.GET = get;
        this.SAVE = save;
        this.UPDATE = update;
        this.DELETE = delete;
        this.AUTH = auth;
    }

    public void validate(Request rq) {
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
