package ru.gold.ordance.course.web.service.web.language;

import ru.gold.ordance.course.base.service.core.LanguageService;
import ru.gold.ordance.course.internal.api.request.EmptyResponse;
import ru.gold.ordance.course.internal.api.request.language.*;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.web.mapper.LanguageMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LanguageWebServiceImpl implements LanguageWebService {
    private final LanguageService service;
    private final LanguageMapper mapper;

    public LanguageWebServiceImpl(LanguageService service, LanguageMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public LanguageGetListResponse findAll() {
        List<Language> allLanguages = service.findAll();

        return LanguageGetListResponse.success(
                allLanguages.stream()
                        .map(mapper::fromLanguage)
                        .collect(Collectors.toList()));
    }

    @Override
    public Response findById(LanguageGetByIdRequest rq) {
        Optional<Language> foundLanguage = service.findByEntityId(rq.getEntityId());

        return process(foundLanguage);
    }

    @Override
    public Response findByName(LanguageGetByNameRequest rq) {
        Optional<Language> foundLanguage = service.findByName(rq.getName());

        return process(foundLanguage);
    }

    @Override
    public LanguageSaveResponse save(LanguageSaveRequest rq) {
        Language savedLanguage = service.save(mapper.toLanguage(rq));

        return LanguageSaveResponse.success(mapper.fromLanguage(savedLanguage));
    }

    @Override
    public LanguageUpdateResponse update(LanguageUpdateRequest rq) {
        Language updatedLanguage = service.update(mapper.toLanguage(rq));

        return LanguageUpdateResponse.success(mapper.fromLanguage(updatedLanguage));
    }

    @Override
    public LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return LanguageDeleteResponse.success();
    }

    private Response process(Optional<Language> language) {
        return language.isPresent()
                ? LanguageGetEntityResponse.success(mapper.fromLanguage(language.get()))
                : new EmptyResponse();
    }
}
