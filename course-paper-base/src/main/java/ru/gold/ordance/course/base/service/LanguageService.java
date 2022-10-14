package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Language;

import java.util.Optional;

public interface LanguageService extends AbstractService<Language> {
    Language findByName(String name);
}
