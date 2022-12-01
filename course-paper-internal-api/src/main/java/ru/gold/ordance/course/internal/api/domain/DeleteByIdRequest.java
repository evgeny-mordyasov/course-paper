package ru.gold.ordance.course.internal.api.domain;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorEntityId;

public interface DeleteByIdRequest extends Request {
    Long getEntityId();

    @Override
    default void validate() {
        errorEntityId(getEntityId());
    }
}
