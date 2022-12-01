package ru.gold.ordance.course.internal.api.domain.classification.request;

import lombok.*;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClassificationUpdateRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String name;

    @Override
    public void validate() {
        errorEntityId(getEntityId());
        errorString(getName(), "name");
    }
}
