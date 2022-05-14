package ru.gold.ordance.course.web.service.mapper;

import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.web.api.language.LanguageSaveRequest;
import ru.gold.ordance.course.web.api.language.LanguageUpdateRequest;
import ru.gold.ordance.course.web.api.language.WebLanguage;

public interface LanguageMapper {
    Language toLanguage(LanguageSaveRequest rq);

    Language toLanguage(LanguageUpdateRequest rq);

    WebLanguage fromLanguage(Language language);
}
