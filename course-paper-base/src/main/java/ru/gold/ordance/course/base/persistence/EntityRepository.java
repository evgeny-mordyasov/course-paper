package ru.gold.ordance.course.base.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<ENTITY> extends JpaRepository<ENTITY, Long> {
    Optional<ENTITY> findById(Long id);
    List<ENTITY> findAll();
}
