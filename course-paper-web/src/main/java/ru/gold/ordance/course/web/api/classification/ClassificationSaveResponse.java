package ru.gold.ordance.course.web.api.classification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Builder
@Getter
@ToString
public class ClassificationSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final WebClassification classification;

    public static ClassificationSaveResponse success(WebClassification classification) {
        return ClassificationSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .classification(classification)
                .build();
    }
}
