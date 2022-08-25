package ru.gold.ordance.course.web.api.file;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Builder
@Getter
@ToString
public class FileSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final WebFile file;

    public static FileSaveResponse success(WebFile file) {
        return FileSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .file(file)
                .build();
    }
}
