package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends EntityRepository<Document> {
    List<Document> findAllByName(String name);
}
