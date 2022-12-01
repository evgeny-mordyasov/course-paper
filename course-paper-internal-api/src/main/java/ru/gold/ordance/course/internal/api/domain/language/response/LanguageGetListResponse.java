package ru.gold.ordance.course.internal.api.domain.language.response;

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
public class LanguageGetListResponse implements Response {
    private static final long serialVersionUID = 1L;

    private final Status status;
    private final List<WebLanguage> list;
    private final Integer total;

    public static LanguageGetListResponse success(List<WebLanguage> list) {
        return LanguageGetListResponse.builder()
                .status(Status.success())
                .list(list)
                .total(list.size())
                .build();
    }
}
