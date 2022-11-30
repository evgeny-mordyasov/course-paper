package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.repository.main.EntityRepository;

@Repository
public interface DocumentRepository extends EntityRepository<Document> {
    default Document preserve(Document document) {
        return defaultPreserve(document);
    }
}
