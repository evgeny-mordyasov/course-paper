package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;

import java.util.Optional;

@Repository
public interface LnkDocumentLanguageRepository extends EntityRepository<LnkDocumentLanguage> {
    Optional<LnkDocumentLanguage> findLnkDocumentLanguageByUrn(String URN);
    Long countLnkDocumentLanguagesByDocument_Id(Long documentId);
}
