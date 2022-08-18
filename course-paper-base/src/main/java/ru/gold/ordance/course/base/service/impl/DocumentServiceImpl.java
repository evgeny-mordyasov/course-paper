package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.exception.NotFoundException;
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
    public Document update(@NotNull Document document) {
        return repository.saveAndFlush(document);
    }

    @Override
    public void deleteById(Long id) {
        Document document = repository.findById(id)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(document.getId());
    }
}
