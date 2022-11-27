package ru.gold.ordance.course.internal.api.request.language;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.internal.api.request.Response;

@Builder
@Getter
@ToString
public class LanguageUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebLanguage object;

    public static LanguageUpdateResponse success(WebLanguage language) {
        return LanguageUpdateResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .object(language)
                .build();
    }
}
