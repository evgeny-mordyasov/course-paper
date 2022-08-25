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
public class LanguageGetEntityResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final WebLanguage language;

    public static LanguageGetEntityResponse success(WebLanguage language) {
        return status().language(language).build();
    }

    public static LanguageGetEntityResponse emptySuccess() {
        return status().build();
    }

    private static LanguageGetEntityResponse.LanguageGetEntityResponseBuilder status() {
        return LanguageGetEntityResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS));
    }
}
