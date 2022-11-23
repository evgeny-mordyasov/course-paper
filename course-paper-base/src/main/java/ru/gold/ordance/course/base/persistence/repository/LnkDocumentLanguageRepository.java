package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.*;

@Repository
public interface LnkDocumentLanguageRepository extends EntityRepository<LnkDocumentLanguage> {
    List<LnkDocumentLanguage> findByDocument_EntityId(Long documentId);
    Optional<LnkDocumentLanguage> findByUrn(String URN);
    Optional<LnkDocumentLanguage> findByDocument_EntityIdAndLanguage_EntityId(Long documentId, Long languageId);
    List<LnkDocumentLanguage> findByDocument_Classification_EntityId(Long classificationId);
    Long countLnkDocumentLanguagesByDocument_EntityId(Long documentId);

    default List<LnkDocumentLanguage> getByDocumentId(Long documentId) {
        return getEntities(findByDocument_EntityId(documentId));
    }

    default LnkDocumentLanguage getByUrn(String URN) {
        return getEntity(findByUrn(URN));
    }

    default LnkDocumentLanguage getByDocumentIdAndLanguageId(Long documentId, Long languageId) {
        return getEntity(findByDocument_EntityIdAndLanguage_EntityId(documentId, languageId));
    }

    @Override
    default LnkDocumentLanguage preserve(LnkDocumentLanguage entity) {
        return EntityRepository.super.preserve(fillEntity(entity));
    }

    default void deleteByUrn(String urn) {
        Optional<LnkDocumentLanguage> lnk = findByUrn(urn);

        if (lnk.isEmpty()) {
            throw new EntityNotFoundException();
        }

        deleteById(lnk.get().getEntityId());
    }

    private LnkDocumentLanguage fillEntity(LnkDocumentLanguage entity) {
        Language language = getLanguageById(entity.getLanguage().getEntityId());
        Document document = getDocumentById(entity.getDocument().getEntityId());

        return entity.toBuilder()
                .withLanguage(language)
                .withDocument(document)
                .build();
    }
}
