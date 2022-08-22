package ru.gold.ordance.course.web.api.classification;

import lombok.*;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.web.api.UpdateRequest;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClassificationUpdateRequest implements UpdateRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;

    @Override
    public void validate() {
        errorEntityId(getEntityId());
        errorString(getName(), "name");
    }
}
