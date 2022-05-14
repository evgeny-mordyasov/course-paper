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
public class LanguageSaveResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static LanguageSaveResponse success() {
        return LanguageSaveResponse.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static LanguageSaveResponse error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LanguageSaveResponse.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}
