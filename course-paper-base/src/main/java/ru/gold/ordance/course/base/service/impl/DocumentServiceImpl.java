package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.persistence.repository.DocumentRepository;
import ru.gold.ordance.course.base.service.DocumentService;

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
    public Document findByEntityId(@NotNull Long entityId) {
        return repository.getByEntityId(entityId);
    }

    @Override
    public List<Document> findByName(@NotNull String name) {
        return repository.findAllByName(name);
    }

    @Override
    public Document save(@NotNull Document document) {
        return repository.preserve(document);
    }

    @Override
    public Document update(@NotNull Document document) {
        return repository.update(document);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}
