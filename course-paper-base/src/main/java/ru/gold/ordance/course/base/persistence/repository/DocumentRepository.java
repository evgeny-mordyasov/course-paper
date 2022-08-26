package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.exception.InternalEntityNotFoundException;

import java.util.List;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getClassificationById;

@Repository
public interface DocumentRepository extends EntityRepository<Document> {
    List<Document> findAllByName(String name);

    @Override
    default Document preserve(Document entity) {
        return EntityRepository.super.preserve(fillEntity(entity));
    }

    @Override
    default Document update(Document entity) {
        return EntityRepository.super.update(fillEntity(entity));
    }

    private Document fillEntity(Document entity) {
        Classification classification = getClassificationById(entity.getClassification().getEntityId());

        if (classification == null) {
            throw new InternalEntityNotFoundException();
        }

        return entity.toBuilder()
                .withClassification(classification)
                .build();
    }
}
