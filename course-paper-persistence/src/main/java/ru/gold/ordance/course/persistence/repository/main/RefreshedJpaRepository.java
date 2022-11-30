package ru.gold.ordance.course.persistence.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import java.util.List;

@NoRepositoryBean
public interface RefreshedJpaRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
    void refresh(ENTITY entity);
    <T extends AbstractEntity> void exists(List<T> entities);
}
