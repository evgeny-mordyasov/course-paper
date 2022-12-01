package ru.gold.ordance.course.internal.api.domain.language.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;

@Builder
@Getter
@ToString
public class LanguageGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final WebLanguage object;

    public static LanguageGetEntityResponse success(WebLanguage language) {
        return LanguageGetEntityResponse.builder()
                .status(Status.success())
                .object(language)
                .build();
    }
}
