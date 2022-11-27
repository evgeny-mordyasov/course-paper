package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.sub.DocumentService;
import ru.gold.ordance.course.persistence.entity.Document;
import ru.gold.ordance.course.persistence.repository.sub.DocumentRepository;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository repository;

    public DocumentServiceImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Document> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Document> findByEntityId(@NotNull Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public Document save(@NotNull Document document) {
        return repository.preserve(document);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}
