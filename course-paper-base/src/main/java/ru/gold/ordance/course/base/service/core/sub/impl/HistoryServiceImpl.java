package ru.gold.ordance.course.base.service.core.sub.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.History;
import ru.gold.ordance.course.persistence.repository.sub.HistoryRepository;
import ru.gold.ordance.course.base.service.core.sub.HistoryService;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository repository;

    public HistoryServiceImpl(HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<History> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<History> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public List<History> findByDocumentId(Long documentId) {
        return repository.findByDocument_EntityId(documentId);
    }

    @Override
    public History save(History entity) {
        return repository.preserve(entity);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}
