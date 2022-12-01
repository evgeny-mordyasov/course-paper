package ru.gold.ordance.course.base.service.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.service.core.*;
import ru.gold.ordance.course.base.service.config.properties.MailSenderProperties;
import ru.gold.ordance.course.persistence.repository.sub.*;
import ru.gold.ordance.course.persistence.config.PersistenceConfiguration;

import java.util.Properties;

@Configuration
@ConfigurationPropertiesScan
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

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
    public HistoryService documentReadingHistoryService(HistoryRepository repository) {
        return new HistoryService(repository);
    }

    @Bean
    public ConfirmationTokenService confirmationTokenService(ConfirmationTokenRepository repository) {
        return new ConfirmationTokenService(repository);
    }

    @Bean
    public MailSenderService emailSenderService(JavaMailSender jms, MailSenderProperties mailSenderProperties) {
        return new MailSenderService(jms, mailSenderProperties);
    }

    @Bean
    public JavaMailSender getJavaMailSender(MailSenderProperties msProp) {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost(msProp.getHost());
        jms.setPort(msProp.getPort());
        jms.setProtocol(msProp.getProtocol());

        jms.setUsername(msProp.getSender());
        jms.setPassword(msProp.getPassword());

        Properties props = jms.getJavaMailProperties();
        props.put("mail.transport.protocol", msProp.getProtocol());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return jms;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}
