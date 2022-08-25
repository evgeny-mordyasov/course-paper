package ru.gold.ordance.course.web.api.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class ClientGetListResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebClient> list;

    private final Integer total;

    public static ClientGetListResponse success(List<WebClient> list) {
        return ClientGetListResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
