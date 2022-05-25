package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Document;

import java.util.List;

public interface DocumentService extends AbstractService<Document> {
    List<Document> findByName(String name);
}
