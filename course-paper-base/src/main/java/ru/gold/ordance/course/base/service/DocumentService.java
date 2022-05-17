package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentService extends AbstractService<Document> {
    List<Document> findByName(String name);
}
