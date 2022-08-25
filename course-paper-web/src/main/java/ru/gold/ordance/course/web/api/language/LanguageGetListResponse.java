package ru.gold.ordance.course.web.api.language;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.common.api.Status;
import ru.gold.ordance.course.common.api.StatusCode;

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
                .status(new Status().withCode(StatusCode.SUCCESS))
                .list(list)
                .total(list.size())
                .build();
    }
}
