CREATE SEQUENCE confirmation_token_sequence;

CREATE TABLE CONFIRMATION_TOKEN (
    ENTITY_ID   BIGINT PRIMARY KEY DEFAULT nextval('confirmation_token_sequence'),
    TOKEN       VARCHAR(128) NOT NULL,
    EXPIRY_DATE TIMESTAMP NOT NULL,
    CLIENT_ID   BIGINT REFERENCES CLIENT(ENTITY_ID) NOT NULL
) TABLESPACE ${tbsTables};

GRANT SELECT, INSERT, UPDATE, DELETE ON CONFIRMATION_TOKEN TO ${appRole};
GRANT SELECT ON CONFIRMATION_TOKEN TO ${readerRole};