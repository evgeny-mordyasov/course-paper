package ru.gold.ordance.course.web.api.classification;

import lombok.*;
import ru.gold.ordance.course.web.api.SaveRequest;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClassificationSaveRequest implements SaveRequest {
    private static final long serialVersionUID = 1L;

    private final String name;
}
