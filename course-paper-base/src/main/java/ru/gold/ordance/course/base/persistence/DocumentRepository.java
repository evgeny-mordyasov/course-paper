package ru.gold.ordance.course.base.persistence;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Document;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends EntityRepository<Document> {
    List<Document> findAllByName(String name);
}
