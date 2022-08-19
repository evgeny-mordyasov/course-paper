CREATE TABLE CLASSIFICATION (
    ENTITY_ID BIGINT PRIMARY KEY,
    NAME      VARCHAR(128) UNIQUE NOT NULL
) TABLESPACE ${tbsTables};

GRANT SELECT, INSERT, UPDATE, DELETE ON CLASSIFICATION TO ${appRole};
GRANT SELECT ON CLASSIFICATION TO ${readerRole};