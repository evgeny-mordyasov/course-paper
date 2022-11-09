package ru.gold.ordance.course.base.service.core.sub.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.History;
import ru.gold.ordance.course.base.persistence.repository.HistoryRepository;
import ru.gold.ordance.course.base.service.core.sub.HistoryService;

import java.util.List;

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
    public History getByEntityId(Long entityId) {
        return repository.getByEntityId(entityId);
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
