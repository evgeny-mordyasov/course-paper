package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.Classification;
import ru.gold.ordance.course.persistence.repository.main.NonDuplicateEntityRepository;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends NonDuplicateEntityRepository<Classification> {
    Optional<Classification> findByName(String name);

    default Classification preserve(Classification entity) {
        return preserve(entity, () -> findByName(entity.getName()));
    }

    default Classification update(Classification entity) {
        return update(entity, () -> findByName(entity.getName()));
    }
}
