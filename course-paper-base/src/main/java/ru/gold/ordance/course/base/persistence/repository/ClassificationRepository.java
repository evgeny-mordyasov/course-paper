package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends EntityRepository<Classification> {
    Optional<Classification> findByName(String name);

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
        if (findByName(entity.getName()).isPresent()) {
            throw new ViolatesConstraintException();
        }
    }
}
