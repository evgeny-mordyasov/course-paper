package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.exception.InternalEntityNotFoundException;

import java.util.List;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.noExistsClassification;

@Repository
public interface DocumentRepository extends EntityRepository<Document> {
    List<Document> findAllByName(String name);

    @Override
    default Document preserve(Document entity) {
        validate(entity);
        return EntityRepository.super.preserve(entity);
    }

    @Override
    default Document update(Document entity) {
        validate(entity);
        return EntityRepository.super.update(entity);
    }

    private void validate(Document entity) {
        long classificationId = entity.getClassification().getEntityId();

        if (noExistsClassification(classificationId)) {
            throw new InternalEntityNotFoundException();
        }
    }
}
