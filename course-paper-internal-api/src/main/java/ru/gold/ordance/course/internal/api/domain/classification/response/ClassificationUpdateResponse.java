package ru.gold.ordance.course.internal.api.domain.classification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;

@Builder
@Getter
@ToString
public class ClassificationUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClassification object;

    public static ClassificationUpdateResponse success(WebClassification classification) {
        return ClassificationUpdateResponse.builder()
                .status(Status.success())
                .object(classification)
                .build();
    }
}
