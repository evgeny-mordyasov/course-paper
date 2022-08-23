package ru.gold.ordance.course.web.service.web.language;

import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface LanguageWebService extends WebService {
    LanguageGetListResponse findAll();
    LanguageGetEntityResponse findById(LanguageGetByIdRequest rq);
    LanguageGetEntityResponse findByName(LanguageGetByNameRequest rq);
    LanguageSaveResponse save(LanguageSaveRequest rq);
    LanguageUpdateResponse update(LanguageUpdateRequest rq);
    LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq);
}
