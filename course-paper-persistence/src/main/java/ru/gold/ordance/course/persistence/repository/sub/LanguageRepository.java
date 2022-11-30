package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.persistence.repository.main.NonDuplicateEntityRepository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends NonDuplicateEntityRepository<Language> {
    Optional<Language> findByName(String name);

    default Language preserve(Language entity) {
        return preserve(entity, () -> findByName(entity.getName()));
    }

    default Language update(Language entity) {
        return update(entity, () -> findByName(entity.getName()));
    }
}
