package ru.gold.ordance.course.web;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.persistence.repository.*;
import ru.gold.ordance.course.base.service.*;
import ru.gold.ordance.course.base.service.impl.*;
import ru.gold.ordance.course.base.spring.annotation.JpaContext;
import ru.gold.ordance.course.web.mapper.config.MapperConfiguration;
import ru.gold.ordance.course.web.service.config.WebServiceConfiguration;

@JpaContext
@Configuration
@Import({WebServiceConfiguration.class, MapperConfiguration.class})
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

    @MockBean
    public AuthenticationManager manager;
}
