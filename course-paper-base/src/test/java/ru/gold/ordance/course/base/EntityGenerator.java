package ru.gold.ordance.course.base;

import ru.gold.ordance.course.common.constants.Role;
import ru.gold.ordance.course.persistence.entity.impl.*;

import static ru.gold.ordance.course.base.utils.TestUtils.randomString;

public final class EntityGenerator {
    private EntityGenerator() {
    }

    public static Client createClient() {
        return createBaseClient().build();
    }

    public static Client createClient(Long id) {
        return createBaseClient()
                .withEntityId(id)
                .build();
    }

    private static Client.ClientBuilder createBaseClient() {
        return Client.builder()
                .withSurname(randomString())
                .withName(randomString())
                .withPatronymic(randomString())
                .withEmail(randomString())
                .withPassword(randomString())
                .withRole(Role.USER)
                .withIsActive(true);
    }

    public static Classification createClassification() {
        return Classification.builder()
                .withName(randomString())
                .build();
    }

    public static Classification createClassification(Long id) {
        return Classification.builder()
                .withEntityId(id)
                .withName(randomString())
                .build();
    }

    public static Language createLanguage() {
        return Language.builder()
                .withName(randomString())
                .build();
    }

    public static Language createLanguage(Long id) {
        return Language.builder()
                .withEntityId(id)
                .withName(randomString())
                .build();
    }

    public static Document createDocument(Classification classification) {
        return Document.builder()
                .withName(randomString())
                .withClassification(classification)
                .build();
    }

    public static LnkDocumentLanguage createLnk(Document doc, Language lang) {
        return LnkDocumentLanguage.builder()
                .withDocument(doc)
                .withLanguage(lang)
                .withUrn(randomString())
                .build();
    }

    public static LnkDocumentLanguage createLnk(Document doc, Language lang, String urn) {
        return LnkDocumentLanguage.builder()
                .withDocument(doc)
                .withLanguage(lang)
                .withUrn(urn)
                .build();
    }
}
