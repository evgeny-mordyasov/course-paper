package ru.gold.ordance.course.internal.api.domain.file.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.file.model.WebFile;

@Builder
@Getter
@ToString
public class FileGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebFile object;

    public static FileGetEntityResponse success(WebFile file) {
        return FileGetEntityResponse.builder()
                .status(Status.success())
                .object(file)
                .build();
    }
}
