package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.Request;

public interface RequestValidate<REQUEST extends Request> {
    void validate(REQUEST rq);
}
