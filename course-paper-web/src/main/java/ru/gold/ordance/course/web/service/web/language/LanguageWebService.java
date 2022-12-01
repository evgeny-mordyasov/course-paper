package ru.gold.ordance.course.web.service.web.language;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.language.request.*;
import ru.gold.ordance.course.internal.api.domain.language.response.LanguageDeleteResponse;
import ru.gold.ordance.course.internal.api.domain.language.response.LanguageGetListResponse;
import ru.gold.ordance.course.internal.api.domain.language.response.LanguageSaveResponse;
import ru.gold.ordance.course.internal.api.domain.language.response.LanguageUpdateResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface LanguageWebService extends WebService {
    LanguageGetListResponse findAll();
    Response findById(LanguageGetByIdRequest rq);
    Response findByName(LanguageGetByNameRequest rq);
    LanguageSaveResponse save(LanguageSaveRequest rq);
    LanguageUpdateResponse update(LanguageUpdateRequest rq);
    LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq);
}
