package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.domain.language.request.LanguageSaveRequest;
import ru.gold.ordance.course.internal.api.domain.language.request.LanguageUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;
import ru.gold.ordance.course.persistence.entity.impl.Language;

public final class LanguageMapper {
    private static final LanguageMapper INSTANCE = new LanguageMapper();

    private LanguageMapper() {
    }

    public static LanguageMapper instance() {
        return INSTANCE;
    }

    public Language toLanguage(LanguageSaveRequest rq) {
        return Language.builder()
                .withName(rq.getName())
                .build();
    }

    public Language toLanguage(LanguageUpdateRequest rq) {
        return Language.builder()
                .withEntityId(rq.getEntityId())
                .withName(rq.getName())
                .build();
    }

    public WebLanguage fromLanguage(Language language) {
        return WebLanguage.builder()
                .withEntityId(language.getEntityId())
                .withName(language.getName())
                .build();
    }
}
