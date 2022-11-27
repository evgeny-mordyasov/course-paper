package ru.gold.ordance.course.internal.api.request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorEntityId;

public interface GetByIdRequest extends GetRequest {
    Long getEntityId();

    @Override
    default void validate() {
        errorEntityId(getEntityId());
    }
}
