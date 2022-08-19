package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.web.api.language.LanguageSaveRequest;
import ru.gold.ordance.course.web.api.language.LanguageUpdateRequest;
import ru.gold.ordance.course.web.api.language.WebLanguage;
import ru.gold.ordance.course.web.mapper.LanguageMapper;

public class LanguageMapperImpl implements LanguageMapper {

    @Override
    public Language toLanguage(LanguageSaveRequest rq) {
        return Language.builder()
                .withName(rq.getName())
                .build();
    }

    @Override
    public Language toLanguage(LanguageUpdateRequest rq) {
        return Language.builder()
                .withEntityId(rq.getEntityId())
                .withName(rq.getName())
                .build();
    }

    @Override
    public WebLanguage fromLanguage(Language lang) {
        return WebLanguage.builder()
                .withEntityId(lang.getEntityId())
                .withName(lang.getName())
                .build();
    }
}
