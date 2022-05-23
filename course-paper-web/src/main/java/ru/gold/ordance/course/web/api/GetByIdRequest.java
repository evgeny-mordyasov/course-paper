package ru.gold.ordance.course.web.api;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorEntityId;

public interface GetByIdRequest extends GetRequest {
    Long getEntityId();

    @Override
    default void validate() {
        errorEntityId(getEntityId());
    }
}
