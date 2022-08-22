CREATE TABLE DOCUMENT_LANGUAGE (
    ENTITY_ID   BIGINT PRIMARY KEY,
    DOCUMENT_ID BIGINT REFERENCES DOCUMENT(ENTITY_ID) NOT NULL,
    LANGUAGE_ID BIGINT REFERENCES LANGUAGE(ENTITY_ID) NOT NULL,
    URN         VARCHAR(512) NOT NULL
) TABLESPACE ${tbsTables};

CREATE UNIQUE INDEX dl_doc_lang_urn_idx
    ON DOCUMENT_LANGUAGE (DOCUMENT_ID, LANGUAGE_ID, URN)
    TABLESPACE ${tbsIdx};

CREATE SEQUENCE lnk_sequence;

GRANT SELECT, INSERT, UPDATE, DELETE ON DOCUMENT_LANGUAGE TO ${appRole};
GRANT SELECT ON DOCUMENT_LANGUAGE TO ${readerRole};