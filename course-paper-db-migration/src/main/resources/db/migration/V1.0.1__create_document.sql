CREATE SEQUENCE document_sequence;

CREATE TABLE DOCUMENT (
    ENTITY_ID         BIGINT PRIMARY KEY DEFAULT nextval('document_sequence'),
    FULL_NAME         VARCHAR(133) NOT NULL,
    NAME              VARCHAR(128) NOT NULL,
    EXTENSION         VARCHAR(5) NOT NULL,
    CLASSIFICATION_ID BIGINT REFERENCES CLASSIFICATION(ENTITY_ID) NOT NULL
) TABLESPACE ${tbsTables};

GRANT SELECT, INSERT, UPDATE, DELETE ON DOCUMENT TO ${appRole};
GRANT SELECT ON DOCUMENT TO ${readerRole};