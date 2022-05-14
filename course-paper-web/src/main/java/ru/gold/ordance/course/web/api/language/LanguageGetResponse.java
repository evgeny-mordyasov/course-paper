package ru.gold.ordance.course.web.api.language;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class LanguageGetResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebLanguage> list;

    private final Integer total;

    public static LanguageGetResponse success(List<WebLanguage> list) {
        return LanguageGetResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }

    public static LanguageGetResponse error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LanguageGetResponse.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}
