CREATE SEQUENCE lnk_sequence;

CREATE TABLE DOCUMENT_LANGUAGE (
    ENTITY_ID   BIGINT PRIMARY KEY DEFAULT nextval('lnk_sequence'),
    DOCUMENT_ID BIGINT REFERENCES DOCUMENT(ENTITY_ID) ON DELETE CASCADE NOT NULL,
    LANGUAGE_ID BIGINT REFERENCES LANGUAGE(ENTITY_ID) NOT NULL,
    URN         VARCHAR(512) NOT NULL
) TABLESPACE ${tbsTables};

CREATE UNIQUE INDEX dl_doc_lang_urn_idx
    ON DOCUMENT_LANGUAGE (DOCUMENT_ID, LANGUAGE_ID, URN)
    TABLESPACE ${tbsIdx};

GRANT SELECT, INSERT, UPDATE, DELETE ON DOCUMENT_LANGUAGE TO ${appRole};
GRANT SELECT ON DOCUMENT_LANGUAGE TO ${readerRole};