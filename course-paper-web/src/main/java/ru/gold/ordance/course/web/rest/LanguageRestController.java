package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.language.*;

public interface LanguageRestController extends AbstractRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByName(String name);
    Response save(LanguageSaveRequest rq);
    Response update(LanguageUpdateRequest rq);
    Response deleteById(Long entityId);
}
