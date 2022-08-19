package ru.gold.ordance.course.web.service.web.language;

import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface LanguageWebService extends WebService {
    LanguageGetResponse findAll();
    LanguageGetResponse findById(LanguageGetByIdRequest rq);
    LanguageGetResponse findByName(LanguageGetByNameRequest rq);
    LanguageSaveResponse save(LanguageSaveRequest rq);
    LanguageUpdateResponse update(LanguageUpdateRequest rq);
    LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq);
}
