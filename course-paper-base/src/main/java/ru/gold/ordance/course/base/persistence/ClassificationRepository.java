package ru.gold.ordance.course.base.persistence;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Classification;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends EntityRepository<Classification> {
    Optional<Classification> findByName(String name);
}
