package ru.gold.ordance.course.internal.api.domain.history.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.history.model.WebHistory;

import java.util.List;

@Builder
@Getter
@ToString
public class HistoryGetListResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final List<WebHistory> list;
    private final Integer total;

    public static HistoryGetListResponse success(List<WebHistory> list) {
        return HistoryGetListResponse.builder()
                .status(Status.success())
                .list(list)
                .total(list.size())
                .build();
    }
}
