package ru.gold.ordance.course.internal.api.request.classification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.internal.api.request.Response;

@Builder
@Getter
@ToString
public class ClassificationGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebClassification object;

    public static ClassificationGetEntityResponse success(WebClassification classification) {
        return ClassificationGetEntityResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .object(classification)
                .build();
    }
}