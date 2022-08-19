package ru.gold.ordance.course.web.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.base.service.*;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;
import ru.gold.ordance.course.web.mapper.ClientMapper;
import ru.gold.ordance.course.web.mapper.FileMapper;
import ru.gold.ordance.course.web.mapper.LanguageMapper;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.web.authorization.config.JwtConfig;
import ru.gold.ordance.course.web.service.web.authorization.config.JwtConfigImpl;
import ru.gold.ordance.course.web.service.web.authorization.impl.AuthorizationWebServiceImpl;
import ru.gold.ordance.course.web.service.web.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.web.authorization.userdetails.UserDetailsServiceImpl;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebService;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebServiceImpl;
import ru.gold.ordance.course.web.service.web.client.ClientWebService;
import ru.gold.ordance.course.web.service.web.client.ClientWebServiceImpl;
import ru.gold.ordance.course.web.service.web.file.FileStorage;
import ru.gold.ordance.course.web.service.web.file.FileWebService;
import ru.gold.ordance.course.web.service.web.file.FileWebServiceImpl;
import ru.gold.ordance.course.web.service.web.language.LanguageWebService;
import ru.gold.ordance.course.web.service.web.language.LanguageWebServiceImpl;

@Configuration
public class WebServiceConfiguration {

    @Bean
    public LanguageWebService languageWebService(LanguageService service, LanguageMapper mapper) {
        return new LanguageWebServiceImpl(service, mapper);
    }

    @Bean
    public ClassificationWebService classificationWebService(ClassificationService service, ClassificationMapper mapper) {
        return new ClassificationWebServiceImpl(service, mapper);
    }

    @Bean
    public ClientWebService clientWebService(ClientService service, ClientMapper mapper) {
        return new ClientWebServiceImpl(service, mapper);
    }

    @Bean
    public FileWebService fileWebService(LnkDocumentLanguageService lnkService,
                                         DocumentService documentService,
                                         FileStorage fileStorage,
                                         FileMapper mapper) {
        return new FileWebServiceImpl(documentService, lnkService, mapper, fileStorage);
    }

    @Bean
    public FileStorage fileStorage(@Value("${spring.servlet.multipart.location}") String storagePath) {
        return new FileStorage(storagePath);
    }

    @Bean
    public AuthorizationWebService authorizationWebService(ClientService service,
                                                           ClientMapper mapper,
                                                           AuthenticationManager manager,
                                                           JwtProvider jwtProvider) {
        return new AuthorizationWebServiceImpl(service, mapper, manager, jwtProvider);
    }

    @Bean
    public JwtProvider jwtProvider(UserDetailsService service, JwtConfig config) {
        return new JwtProvider(service, config);
    }

    @Bean
    public JwtConfig jwtConfig(@Value("${jwt.secret}") String secret,
                               @Value("${jwt.expirationMs}") Long expirationMs) {
        return new JwtConfigImpl(secret, expirationMs);
    }

    @Bean
    public UserDetailsService userDetailsService(ClientService service) {
        return new UserDetailsServiceImpl(service);
    }
}