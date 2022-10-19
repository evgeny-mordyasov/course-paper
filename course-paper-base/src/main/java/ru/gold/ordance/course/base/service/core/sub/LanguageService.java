package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.service.core.UpdatableEntityService;

public interface LanguageService extends UpdatableEntityService<Language> {
    Language findByName(String name);
}
