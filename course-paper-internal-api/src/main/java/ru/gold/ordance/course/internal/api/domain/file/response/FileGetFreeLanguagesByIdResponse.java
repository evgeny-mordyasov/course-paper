package ru.gold.ordance.course.internal.api.domain.file.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;

import java.util.List;

@Builder
@Getter
@ToString
public class FileGetFreeLanguagesByIdResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final List<WebLanguage> list;

    public static FileGetFreeLanguagesByIdResponse success(List<WebLanguage> list) {
        return FileGetFreeLanguagesByIdResponse.builder()
                .status(Status.success())
                .list(list)
                .build();
    }
}
