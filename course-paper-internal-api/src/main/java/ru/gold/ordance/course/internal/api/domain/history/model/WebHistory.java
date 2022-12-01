package ru.gold.ordance.course.internal.api.domain.history.model;

import lombok.*;
import ru.gold.ordance.course.internal.api.domain.client.model.WebClient;
import ru.gold.ordance.course.internal.api.domain.file.model.WebDocument;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final WebClient client;
    private final WebDocument document;
    private final WebLanguage language;
    private final LocalDateTime readingTime;
}
