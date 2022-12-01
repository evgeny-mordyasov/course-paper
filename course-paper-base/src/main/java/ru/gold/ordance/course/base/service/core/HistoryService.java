package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.History;
import ru.gold.ordance.course.persistence.repository.sub.HistoryRepository;

import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class HistoryService {
    private final HistoryRepository repository;

    public HistoryService(HistoryRepository repository) {
        this.repository = repository;
    }

    public List<History> findAll() {
        return repository.findAll();
    }

    public List<History> findByDocumentId(Long documentId) {
        return repository.findByDocument_EntityId(documentId);
    }

    public History save(History obj) {
        return repository.preserve(obj);
    }
}
