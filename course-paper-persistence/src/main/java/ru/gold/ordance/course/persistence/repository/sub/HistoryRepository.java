package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.History;
import ru.gold.ordance.course.persistence.repository.main.EntityRepository;

import java.util.List;

@Repository
public interface HistoryRepository extends EntityRepository<History> {
    List<History> findByDocument_EntityId(Long documentId);

    default History preserve(History history) {
        return defaultPreserve(history);
    }
}
