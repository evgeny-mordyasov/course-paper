package ru.gold.ordance.course.web.api.history;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.Response;

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
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
