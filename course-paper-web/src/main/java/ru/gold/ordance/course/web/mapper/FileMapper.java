package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.dto.File;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FileMapper {
    Document toDocument(File stored, Long classificationId);
    LnkDocumentLanguage toLnk(Long documentId, Long languageId, String urn);
    WebFile toWebFile(Document document, List<LnkDocumentLanguage> documentLanguages);
    List<WebFile> toWebFile(Set<Map.Entry<Long, List<LnkDocumentLanguage>>> set);
}
