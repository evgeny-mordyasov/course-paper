package ru.gold.ordance.course.web.api.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClassificationGetByNameRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final String name;
}
