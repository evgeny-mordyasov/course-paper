package ru.gold.ordance.course.internal.api.domain.classification.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.GetByIdRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClassificationGetByIdRequest implements GetByIdRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
