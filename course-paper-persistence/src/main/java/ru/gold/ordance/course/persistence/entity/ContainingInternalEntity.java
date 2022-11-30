package ru.gold.ordance.course.persistence.entity;

import java.util.List;

public interface ContainingInternalEntity {
    List<AbstractEntity> getInternalEntities();
}
