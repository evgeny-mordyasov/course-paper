package ru.gold.ordance.course.internal.api.domain.history.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.history.model.WebHistory;

@Builder
@Getter
@ToString
public class HistorySaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebHistory history;

    public static HistorySaveResponse success(WebHistory history) {
        return HistorySaveResponse.builder()
                .status(Status.success())
                .history(history)
                .build();
    }
}
