package ru.gold.ordance.course.web.api.language;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

@Builder
@Getter
@ToString
public class LanguageSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebLanguage language;

    public static LanguageSaveResponse success(WebLanguage language) {
        return LanguageSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .language(language)
                .build();
    }
}
