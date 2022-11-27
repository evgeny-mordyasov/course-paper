package ru.gold.ordance.course.internal.api.request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorEntityId;

public interface DeleteByIdRequest extends DeleteRequest {
    Long getEntityId();

    @Override
    default void validate() {
        errorEntityId(getEntityId());
    }
}
