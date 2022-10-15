package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Language;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getEntity;

@Repository
public interface LanguageRepository extends EntityDuplicateRepository<Language> {
    Optional<Language> findByName(String name);

    default Language getByName(String name) {
        return getEntity(findByName(name));
    }

    default Language preserve(Language entity) {
        return preserve(entity, () -> findByName(entity.getName()));
    }

    @Override
    default Language update(Language entity) {
        return update(entity, () -> findByName(entity.getName()));
    }
}
