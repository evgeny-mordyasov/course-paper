package ru.gold.ordance.course.web.service.language;

import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.service.LanguageService;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.service.mapper.LanguageMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageWebServiceImpl implements LanguageWebService {
    private final LanguageService service;

    private final LanguageMapper mapper;

    public LanguageWebServiceImpl(LanguageService service, LanguageMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public LanguageGetResponse findAll() {
        List<Language> allLanguages = service.findAll();

        return LanguageGetResponse.success(
                allLanguages.stream()
                        .map(mapper::fromLanguage)
                        .collect(Collectors.toList()));
    }

    @Override
    public LanguageGetResponse findById(LanguageGetByIdRequest rq) {
        Optional<Language> foundLanguage = service.findById(rq.getEntityId());

        return search(foundLanguage);
    }

    @Override
    public LanguageGetResponse findByName(LanguageGetByNameRequest rq) {
        Optional<Language> foundLanguage = service.findByName(rq.getName());

        return search(foundLanguage);
    }

    @Override
    public LanguageSaveResponse save(LanguageSaveRequest rq) {
        Long entityId = service.save(mapper.toLanguage(rq)).getId();

        return LanguageSaveResponse.success(entityId);
    }

    @Override
    public LanguageUpdateResponse update(LanguageUpdateRequest rq) {
        service.update(mapper.toLanguage(rq));

        return LanguageUpdateResponse.success();
    }

    @Override
    public LanguageDeleteResponse deleteById(LanguageDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return LanguageDeleteResponse.success();
    }

    private LanguageGetResponse search(Optional<Language> language) {
        return LanguageGetResponse.success(language.isEmpty()
                ? Collections.emptyList()
                : Collections.singletonList(mapper.fromLanguage(language.get())));
    }
}
