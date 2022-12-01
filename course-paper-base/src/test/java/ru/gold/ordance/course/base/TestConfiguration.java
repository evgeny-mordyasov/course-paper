package ru.gold.ordance.course.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.service.core.*;
import ru.gold.ordance.course.persistence.repository.sub.*;
import ru.gold.ordance.course.persistence.annotation.JpaContext;

@JpaContext
@Configuration
public class TestConfiguration {

    @Bean
    public ClassificationService classificationService(ClassificationRepository repository) {
        return new ClassificationService(repository);
    }

    @Bean
    public ClientService clientService(ClientRepository repository) {
        return new ClientService(repository, passwordEncoder());
    }

    @Bean
    public DocumentService documentService(DocumentRepository repository) {
        return new DocumentService(repository);
    }

    @Bean
    public LanguageService languageService(LanguageRepository repository) {
        return new LanguageService(repository);
    }

    @Bean
    public LnkDocumentLanguageService lnkDocumentLanguageService(LnkDocumentLanguageRepository repository) {
        return new LnkDocumentLanguageService(repository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}