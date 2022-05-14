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
        List<Language> list = service.findAll();

        if (list.isEmpty()) {
            return LanguageGetResponse.success(Collections.emptyList());
        } else {
            List<WebLanguage> webClients = list.stream()
                    .map(mapper::fromLanguage)
                    .collect(Collectors.toList());

            return LanguageGetResponse.success(webClients);
        }
    }

    @Override
    public LanguageGetResponse findById(LanguageGetByIdRequest rq) {
        Optional<Language> found = service.findById(rq.getEntityId());

        return search(found);
    }

    @Override
    public LanguageGetResponse findByName(LanguageGetByNameRequest rq) {
        Optional<Language> found = service.findByName(rq.getName());

        return search(found);
    }

    @Override
    public LanguageSaveResponse save(LanguageSaveRequest rq) {
        service.save(mapper.toLanguage(rq));

        return LanguageSaveResponse.success();
    }

    @Override
    public LanguageUpdateResponse update(LanguageUpdateRequest rq) {
        service.update(mapper.toLanguage(rq));

        return LanguageUpdateResponse.success();
    }

    @Override
    public LanguageDeleteByIdResponse deleteById(LanguageDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return LanguageDeleteByIdResponse.success();
    }

    private LanguageGetResponse search(Optional<Language> found) {
        if (found.isEmpty()) {
            return LanguageGetResponse.success(Collections.emptyList());
        } else {
            return LanguageGetResponse.success(Collections.singletonList(mapper.fromLanguage(found.get())));
        }
    }
}
