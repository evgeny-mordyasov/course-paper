package ru.gold.ordance.course.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.persistence.repository.*;
import ru.gold.ordance.course.base.service.*;
import ru.gold.ordance.course.base.service.impl.*;
import ru.gold.ordance.course.base.spring.annotation.JpaContext;

@JpaContext
@Configuration
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