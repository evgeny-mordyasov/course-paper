package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.request.language.LanguageSaveRequest;
import ru.gold.ordance.course.internal.api.request.language.LanguageUpdateRequest;
import ru.gold.ordance.course.internal.api.request.language.WebLanguage;
import ru.gold.ordance.course.persistence.entity.impl.Language;

public interface LanguageMapper {
    Language toLanguage(LanguageSaveRequest rq);

    Language toLanguage(LanguageUpdateRequest rq);

    WebLanguage fromLanguage(Language language);
}
