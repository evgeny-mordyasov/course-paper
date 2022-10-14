package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends EntityRepository<Classification> {
    Optional<Classification> findByName(String name);

    default Classification getByName(String name) {
        Optional<Classification> entity = findByName(name);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    @Override
    default Classification preserve(Classification entity) {
        validate(entity);
        return EntityRepository.super.preserve(entity);
    }

    @Override
    default Classification update(Classification entity) {
        validate(entity);
        return EntityRepository.super.update(entity);
    }

    private void validate(Classification entity) {
        Optional<Classification> found = findByName(entity.getName());

        if (found.isPresent() && !found.get().getEntityId().equals(entity.getEntityId())) {
            throw new ViolatesConstraintException();
        }
    }
}
