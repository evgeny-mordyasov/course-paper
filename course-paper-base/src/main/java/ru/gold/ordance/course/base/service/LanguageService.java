package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Language;

import java.util.Optional;

public interface LanguageService extends AbstractService<Language> {
    Optional<Language> findByName(String name);
}
