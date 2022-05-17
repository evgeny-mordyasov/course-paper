package ru.gold.ordance.course.base.persistence.config.dialect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbDialectConfigImpl implements DbDialectConfig {
    private final DbDialect dbDialect;

    public DbDialectConfigImpl(@Value("${persistence.dialect:}") DbDialect dbDialect) {
        this.dbDialect = dbDialect;
    }

    @Override
    public DbDialect dbDialect() {
        return dbDialect;
    }
}
