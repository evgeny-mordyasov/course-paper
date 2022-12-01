package ru.gold.ordance.course.internal.api.domain.language.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.internal.api.domain.Response;

@Builder
@Getter
@ToString
public class LanguageDeleteResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static LanguageDeleteResponse success() {
        return LanguageDeleteResponse.builder()
                .status(Status.success())
                .build();
    }
}
