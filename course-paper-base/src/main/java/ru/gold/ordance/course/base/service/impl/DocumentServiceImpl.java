package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.persistence.DocumentRepository;
import ru.gold.ordance.course.base.service.DocumentService;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<Document> findById(@NotNull Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Document> findByName(@NotNull String name) {
        return repository.findAllByName(name);
    }

    @Override
    public Document save(@NotNull Document document) {
        return repository.saveAndFlush(document);
    }

    @Override
    public void update(@NotNull Document document) {
        repository.saveAndFlush(document);
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresent(document -> repository.deleteById(document.getId()));
    }
}
