package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.language.*;

public interface LanguageRestController extends AbstractRestController {
    LanguageGetResponse findAll();
    LanguageGetResponse findById(Long entityId);
    LanguageGetResponse findByName(String name);
    LanguageSaveResponse save(LanguageSaveRequest rq);
    LanguageUpdateResponse update(LanguageUpdateRequest rq);
    LanguageDeleteByIdResponse deleteById(Long entityId);
}
