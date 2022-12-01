package ru.gold.ordance.course.internal.api.domain.file.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;

@Builder
@Getter
@ToString
public class FileDeleteResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static FileDeleteResponse success() {
        return FileDeleteResponse.builder()
                .status(Status.success())
                .build();
    }
}
