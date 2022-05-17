package ru.gold.ordance.course.web.service.mapper;

import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.WebFile;

public interface FileMapper {

    Document toDocument(FileSaveRequest rq);

    WebFile toWebFile(LnkDocumentLanguage lnk);
}
