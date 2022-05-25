package ru.gold.ordance.course.web.api.file;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

@Builder
@Getter
@ToString
public class FileSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static FileSaveResponse success() {
        return FileSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }
}
