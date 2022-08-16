CREATE TABLE CLIENT (
    ID          BIGINT PRIMARY KEY,
    SURNAME     VARCHAR(128) NOT NULL,
    NAME        VARCHAR(128) NOT NULL,
    PATRONYMIC  VARCHAR(128) NOT NULL,
    EMAIL       VARCHAR(128) NOT NULL,
    PASSWORD    VARCHAR(512) NOT NULL,
    START_DATE  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    UPDATE_DATE TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    IS_ACTIVE   BOOLEAN DEFAULT TRUE
) TABLESPACE ${tbsTables};

CREATE UNIQUE INDEX EMAIL_idx
    ON CLIENT(EMAIL)
    TABLESPACE ${tbsIdx};

GRANT SELECT, INSERT, UPDATE, DELETE ON CLIENT TO ${appRole};
GRANT SELECT ON CLIENT TO ${readerRole};