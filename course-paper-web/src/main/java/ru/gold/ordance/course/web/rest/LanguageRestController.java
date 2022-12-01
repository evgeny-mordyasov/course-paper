package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.language.request.LanguageSaveRequest;
import ru.gold.ordance.course.internal.api.domain.language.request.LanguageUpdateRequest;

public interface LanguageRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByName(String name);
    Response save(LanguageSaveRequest rq);
    Response update(LanguageUpdateRequest rq);
    Response deleteById(Long entityId);
}
