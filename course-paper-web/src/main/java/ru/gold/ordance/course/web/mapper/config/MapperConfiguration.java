package ru.gold.ordance.course.web.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;
import ru.gold.ordance.course.web.mapper.ClientMapper;
import ru.gold.ordance.course.web.mapper.FileMapper;
import ru.gold.ordance.course.web.mapper.LanguageMapper;
import ru.gold.ordance.course.web.mapper.impl.ClassificationMapperImpl;
import ru.gold.ordance.course.web.mapper.impl.ClientMapperImpl;
import ru.gold.ordance.course.web.mapper.impl.FileMapperImpl;
import ru.gold.ordance.course.web.mapper.impl.LanguageMapperImpl;

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
}
