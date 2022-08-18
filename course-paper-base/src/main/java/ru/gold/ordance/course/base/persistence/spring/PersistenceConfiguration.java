package ru.gold.ordance.course.base.persistence.spring;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gold.ordance.course.base.persistence.config.DbConnectionConfiguration;
import ru.gold.ordance.course.base.persistence.PersistenceHelper;
import ru.gold.ordance.course.common.utils.PackageBeanContext;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(PackageBeanContext.REPOSITORIES_PACKAGE)
@EntityScan(PackageBeanContext.ENTITIES_PACKAGE)
@Import(ConnectionConfiguration.class)
public class PersistenceConfiguration {

    @Bean
    public DataSource dataSource(DbConnectionConfiguration connectionConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(connectionConfig.url());
        hikariDataSource.setUsername(connectionConfig.username());
        hikariDataSource.setPassword(connectionConfig.password());

        return hikariDataSource;
    }

    @Bean
    public PersistenceHelper persistenceHelper(EntityManager entityManager) {
        return new PersistenceHelper(entityManager);
    }
}
