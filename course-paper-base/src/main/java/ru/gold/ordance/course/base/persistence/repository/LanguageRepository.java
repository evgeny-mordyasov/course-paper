package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

@Repository
public interface LanguageRepository extends EntityRepository<Language> {
    Optional<Language> findByName(String name);

    default Language getByName(String name) {
        Optional<Language> entity = findByName(name);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    @Override
    default Language preserve(Language entity) {
        validate(entity);
        return EntityRepository.super.preserve(entity);
    }

    @Override
    default Language update(Language entity) {
        validate(entity);
        return EntityRepository.super.update(entity);
    }

    private void validate(Language entity) {
        Optional<Language> found = findByName(entity.getName());

        if (found.isPresent() && !found.get().getEntityId().equals(entity.getEntityId())) {
            throw new ViolatesConstraintException();
        }
    }
}
