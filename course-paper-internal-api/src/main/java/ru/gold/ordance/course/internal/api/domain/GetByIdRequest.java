package ru.gold.ordance.course.internal.api.domain;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorEntityId;

public interface GetByIdRequest extends Request {
    Long getEntityId();

    @Override
    default void validate() {
        errorEntityId(getEntityId());
    }
}
