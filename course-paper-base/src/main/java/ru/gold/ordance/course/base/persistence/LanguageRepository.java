package ru.gold.ordance.course.base.persistence;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Language;

import java.util.Optional;

@Repository
public interface LanguageRepository extends EntityRepository<Language> {
    Optional<Language> findByName(String name);
}
