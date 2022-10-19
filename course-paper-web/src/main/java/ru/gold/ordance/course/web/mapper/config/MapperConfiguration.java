package ru.gold.ordance.course.web.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.web.mapper.*;
import ru.gold.ordance.course.web.mapper.impl.*;

@Configuration
public class MapperConfiguration {

    @Bean
    public ClassificationMapper classificationMapper() {
        return new ClassificationMapperImpl();
    }

    @Bean
    public LanguageMapper languageMapper() {
        return new LanguageMapperImpl();
    }

    @Bean
    public FileMapper fileMapper() {
        return new FileMapperImpl();
    }

    @Bean
    public ClientMapper clientMapper() {
        return new ClientMapperImpl();
    }

    @Bean
    public HistoryMapper historyMapper() {
        return new HistoryMapperImpl();
    }
}
