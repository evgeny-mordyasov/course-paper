package ru.gold.ordance.course.base;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.persistence.repository.*;
import ru.gold.ordance.course.base.service.*;
import ru.gold.ordance.course.base.service.impl.*;
import ru.gold.ordance.course.common.utils.PackageBeanContext;

@Configuration
@ComponentScan(PackageBeanContext.REPOSITORIES_PACKAGE)
@EntityScan(PackageBeanContext.ENTITIES_PACKAGE)
@EnableJpaRepositories(PackageBeanContext.REPOSITORIES_PACKAGE)
public class TestConfiguration {

    @Bean
    public ClassificationService classificationService(ClassificationRepository repository) {
        return new ClassificationServiceImpl(repository);
    }

    @Bean
    public ClientService clientService(ClientRepository repository) {
        return new ClientServiceImpl(repository, passwordEncoder());
    }

    @Bean
    public DocumentService documentService(DocumentRepository repository) {
        return new DocumentServiceImpl(repository);
    }

    @Bean
    public LanguageService languageService(LanguageRepository repository) {
        return new LanguageServiceImpl(repository);
    }

    @Bean
    public LnkDocumentLanguageService lnkDocumentLanguageService(LnkDocumentLanguageRepository repository) {
        return new LnkDocumentLanguageServiceImpl(repository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}