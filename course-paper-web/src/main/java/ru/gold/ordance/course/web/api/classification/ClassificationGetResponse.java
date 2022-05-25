package ru.gold.ordance.course.web.api.classification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class ClassificationGetResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebClassification> list;

    private final Integer total;

    public static ClassificationGetResponse success(List<WebClassification> list) {
        return ClassificationGetResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
