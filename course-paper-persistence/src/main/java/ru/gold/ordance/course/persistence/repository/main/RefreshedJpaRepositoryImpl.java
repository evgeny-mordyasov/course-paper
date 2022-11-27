package ru.gold.ordance.course.persistence.repository.main;

import lombok.NonNull;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

public class RefreshedJpaRepositoryImpl<ENTITY extends AbstractEntity> extends SimpleJpaRepository<ENTITY, Long> implements RefreshedJpaRepository<ENTITY> {
    private final EntityManager entityManager;

    public RefreshedJpaRepositoryImpl(JpaEntityInformation<ENTITY, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void refresh(ENTITY entity) {
        entityManager.refresh(entity);
    }

    @Override
    public <T extends AbstractEntity> void exists(List<T> entities) {
        entities.forEach(this::exists);
    }

    @Override
    public <T extends AbstractEntity> void exists(@NonNull T entity) {
        if (Objects.isNull(entity.getEntityId())) {
            return;
        }

        Object obj = entityManager.find(entity.getClass(), entity.getEntityId());

        if (obj == null) {
            throw new EntityNotFoundException();
        }
    }
}
