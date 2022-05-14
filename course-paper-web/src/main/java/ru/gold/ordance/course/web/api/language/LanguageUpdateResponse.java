package ru.gold.ordance.course.web.api.language;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;

@Builder
@Getter
@ToString
public class LanguageUpdateResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static LanguageUpdateResponse success() {
        return LanguageUpdateResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static LanguageUpdateResponse error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LanguageUpdateResponse.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}
