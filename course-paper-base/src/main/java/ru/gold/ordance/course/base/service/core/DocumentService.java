package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.repository.sub.DocumentRepository;

import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class DocumentService {
    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Optional<Document> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    public Document save(Document obj) {
        return repository.preserve(obj);
    }

    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}
