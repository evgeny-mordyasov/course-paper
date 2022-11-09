package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getEntity;

@Repository
public interface ClassificationRepository extends NonDuplicateEntityRepository<Classification> {
    Optional<Classification> findByName(String name);

    default Classification getByName(String name) {
        return getEntity(findByName(name));
    }

    @Override
    default Classification preserve(Classification entity) {
        return preserve(entity, () -> findByName(entity.getName()));
    }

    @Override
    default Classification update(Classification entity) {
        return update(entity, () -> findByName(entity.getName()));
    }
}
