package ru.gold.ordance.course.web.service.web.language;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface LanguageWebService extends WebService {
    LanguageGetListResponse findAll();
    Response findById(LanguageGetByIdRequest rq);
    Response findByName(LanguageGetByNameRequest rq);
    LanguageSaveResponse save(LanguageSaveRequest rq);
    LanguageUpdateResponse update(LanguageUpdateRequest rq);
    LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq);
}
