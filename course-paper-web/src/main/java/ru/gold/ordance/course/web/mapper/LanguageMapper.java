package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.domain.language.request.LanguageSaveRequest;
import ru.gold.ordance.course.internal.api.domain.language.request.LanguageUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;
import ru.gold.ordance.course.persistence.entity.impl.Language;

public interface LanguageMapper {
    Language toLanguage(LanguageSaveRequest rq);

    Language toLanguage(LanguageUpdateRequest rq);

    WebLanguage fromLanguage(Language language);
}
