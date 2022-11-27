package ru.gold.ordance.course.base.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.service.core.sub.*;
import ru.gold.ordance.course.base.service.core.sub.impl.*;
import ru.gold.ordance.course.common.utils.PackageBeanContext;
import ru.gold.ordance.course.persistence.repository.sub.*;
import ru.gold.ordance.course.persistence.spring.PersistenceConfiguration;

import java.util.Properties;

@Configuration
@ComponentScan(PackageBeanContext.HELPERS_PACKAGE)
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

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
    public HistoryService documentReadingHistoryService(HistoryRepository repository) {
        return new HistoryServiceImpl(repository);
    }

    @Bean
    public ConfirmationTokenService confirmationTokenService(ConfirmationTokenRepository repository) {
        return new ConfirmationTokenServiceImpl(repository);
    }

    @Bean
    public EmailSenderService emailSenderService(JavaMailSender javaMailSender) {
        return new EmailSenderServiceImpl(javaMailSender);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");

        mailSender.setUsername("course.paper.asu@mail.ru");
        mailSender.setPassword("vXX6Aas5ErCy9ndrTVPU");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}
