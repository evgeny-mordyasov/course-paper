package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
    Optional<ENTITY> findByEntityId(Long entityId);
    List<ENTITY> findAll();
}
