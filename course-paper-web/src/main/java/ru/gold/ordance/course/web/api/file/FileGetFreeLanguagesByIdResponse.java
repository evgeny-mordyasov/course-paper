package ru.gold.ordance.course.web.api.file;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.language.WebLanguage;

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
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .build();
    }
}