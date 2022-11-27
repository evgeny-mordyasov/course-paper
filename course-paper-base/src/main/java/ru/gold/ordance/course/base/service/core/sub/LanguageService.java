package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.service.core.UpdatableEntityService;
import ru.gold.ordance.course.persistence.entity.Language;

import java.util.Optional;

public interface LanguageService extends UpdatableEntityService<Language> {
    Optional<Language> findByName(String name);
}
