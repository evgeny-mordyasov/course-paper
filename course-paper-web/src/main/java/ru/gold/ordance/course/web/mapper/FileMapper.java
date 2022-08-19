package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.WebFile;

public interface FileMapper {
    Document toDocument(FileSaveRequest rq);

    LnkDocumentLanguage toLnk(Document document, Long languageId, String URN);

    WebFile toWebFile(LnkDocumentLanguage lnk);
}
