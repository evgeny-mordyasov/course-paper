package ru.gold.ordance.course.web.api.history;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.Response;

@Builder
@Getter
@ToString
public class HistoryGetNumberOfDownloadsResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final Integer downloads;

    public static HistoryGetNumberOfDownloadsResponse success(Integer downloads) {
        return HistoryGetNumberOfDownloadsResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .downloads(downloads)
                .build();
    }
}
