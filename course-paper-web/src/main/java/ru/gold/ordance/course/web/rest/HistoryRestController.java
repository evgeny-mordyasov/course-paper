package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.domain.Response;

public interface HistoryRestController {
    Response findAll();
    Response getNumberOfDownloadsByDocumentId(Long documentId);
}
