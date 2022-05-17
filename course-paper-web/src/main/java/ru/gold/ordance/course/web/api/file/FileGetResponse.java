package ru.gold.ordance.course.web.api.file;

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
public class FileGetResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebFile> list;

    private final Integer total;

    public static FileGetResponse success(List<WebFile> list) {
        return FileGetResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }

    public static FileGetResponse error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return FileGetResponse.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}
