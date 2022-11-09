package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;

public interface HistoryRestController {
    Response findAll();
    Response getNumberOfDownloadsByDocumentId(Long documentId);
}
