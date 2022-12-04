package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;
import ru.gold.ordance.course.persistence.repository.main.EntityRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LnkDocumentLanguageRepository extends EntityRepository<LnkDocumentLanguage> {
    List<LnkDocumentLanguage> findByDocument_EntityId(Long documentId);
    List<LnkDocumentLanguage> findByDocument_Classification_EntityId(Long classificationId);
    Optional<LnkDocumentLanguage> findByDocument_EntityIdAndLanguage_EntityId(Long documentId, Long languageId);
    Long countLnkDocumentLanguagesByDocument_EntityId(Long documentId);

    default LnkDocumentLanguage preserve(LnkDocumentLanguage lnkDocumentLanguage) {
        return defaultPreserve(lnkDocumentLanguage);
    }
}
