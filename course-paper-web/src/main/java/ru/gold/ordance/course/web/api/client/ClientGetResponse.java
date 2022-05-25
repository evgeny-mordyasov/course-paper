package ru.gold.ordance.course.web.api.client;

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
public class ClientGetResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebClient> list;

    private final Integer total;

    public static ClientGetResponse success(List<WebClient> list) {
        return ClientGetResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
