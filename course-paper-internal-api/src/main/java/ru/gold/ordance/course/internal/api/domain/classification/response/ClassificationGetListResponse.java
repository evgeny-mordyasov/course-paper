package ru.gold.ordance.course.internal.api.domain.classification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;

import java.util.List;

@Builder
@Getter
@ToString
public class ClassificationGetListResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final List<WebClassification> list;
    private final Integer total;

    public static ClassificationGetListResponse success(List<WebClassification> list) {
        return ClassificationGetListResponse.builder()
                .status(Status.success())
                .list(list)
                .total(list.size())
                .build();
    }
}
