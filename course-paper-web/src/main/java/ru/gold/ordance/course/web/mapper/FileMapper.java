package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.dto.File;
import ru.gold.ordance.course.internal.api.domain.file.model.WebFile;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FileMapper {
    Document toDocument(File stored, Long classificationId);
    LnkDocumentLanguage toLnk(Document document, Long languageId, String urn);
    LnkDocumentLanguage toLnk(Long documentId, Long languageId, String urn);
    WebFile toWebFile(Document document, List<LnkDocumentLanguage> documentLanguages);
    List<WebFile> toWebFile(Set<Map.Entry<Long, List<LnkDocumentLanguage>>> set);
}
