package ru.gold.ordance.course.web.api.history;

import lombok.*;
import ru.gold.ordance.course.web.api.client.WebClient;
import ru.gold.ordance.course.web.api.file.WebDocument;
import ru.gold.ordance.course.web.api.language.WebLanguage;

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
