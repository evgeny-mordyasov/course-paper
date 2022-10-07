package ru.gold.ordance.course.web.api.file;

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
public class FileGetListResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebFile> list;

    private final Integer total;

    public static FileGetListResponse success(List<WebFile> list) {
        return FileGetListResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
