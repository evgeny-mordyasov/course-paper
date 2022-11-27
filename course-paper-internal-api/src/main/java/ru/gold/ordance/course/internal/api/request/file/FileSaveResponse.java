package ru.gold.ordance.course.internal.api.request.file;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.internal.api.request.Response;

@Builder
@Getter
@ToString
public class FileSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebFile object;

    public static FileSaveResponse success(WebFile file) {
        return FileSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .object(file)
                .build();
    }
}
