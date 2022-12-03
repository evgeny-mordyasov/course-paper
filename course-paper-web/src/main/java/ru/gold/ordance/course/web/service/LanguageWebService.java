package ru.gold.ordance.course.web.service;

import ru.gold.ordance.course.base.service.core.LanguageService;
import ru.gold.ordance.course.internal.api.domain.EmptySuccessfulResponse;
import ru.gold.ordance.course.internal.api.domain.language.request.*;
import ru.gold.ordance.course.internal.api.domain.language.response.*;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.mapper.LanguageMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LanguageWebService implements WebService {
    private final LanguageService service;
    private final LanguageMapper mapper;

    public LanguageWebService(LanguageService service) {
        this.service = service;
        this.mapper = LanguageMapper.instance();
    }

    public LanguageGetListResponse findAll() {
        List<Language> allLanguages = service.findAll();

        return LanguageGetListResponse.success(
                allLanguages.stream()
                        .map(mapper::fromLanguage)
                        .collect(Collectors.toList()));
    }

    public Response findById(LanguageGetByIdRequest rq) {
        Optional<Language> foundLanguage = service.findByEntityId(rq.getEntityId());

        return process(foundLanguage);
    }

    public Response findByName(LanguageGetByNameRequest rq) {
        Optional<Language> foundLanguage = service.findByName(rq.getName());

        return process(foundLanguage);
    }

    public LanguageSaveResponse save(LanguageSaveRequest rq) {
        Language savedLanguage = service.save(mapper.toLanguage(rq));

        return LanguageSaveResponse.success(mapper.fromLanguage(savedLanguage));
    }

    public LanguageUpdateResponse update(LanguageUpdateRequest rq) {
        Language updatedLanguage = service.update(mapper.toLanguage(rq));

        return LanguageUpdateResponse.success(mapper.fromLanguage(updatedLanguage));
    }

    public LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return LanguageDeleteResponse.success();
    }

    private Response process(Optional<Language> language) {
        return language.isPresent()
                ? LanguageGetEntityResponse.success(mapper.fromLanguage(language.get()))
                : new EmptySuccessfulResponse();
    }
}
