package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

@Repository
public interface LanguageRepository extends EntityRepository<Language> {
    Optional<Language> findByName(String name);

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
        if (findByName(entity.getName()).isPresent()) {
            throw new ViolatesConstraintException();
        }
    }
}
