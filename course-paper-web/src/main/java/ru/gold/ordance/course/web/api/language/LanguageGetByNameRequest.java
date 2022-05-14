package ru.gold.ordance.course.web.api.language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetRequest;

@AllArgsConstructor
@Getter
@ToString
public class LanguageGetByNameRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final String name;
}
