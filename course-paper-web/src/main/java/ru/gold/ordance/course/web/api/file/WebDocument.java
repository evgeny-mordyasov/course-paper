package ru.gold.ordance.course.web.api.file;

import lombok.*;
import ru.gold.ordance.course.web.api.classification.WebClassification;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String fullName;
    private final String name;
    private final String extension;
    private final WebClassification classification;
}
