package ru.gold.ordance.course.web.service.authorization.jwt.rule;

public interface Authority {
    void apply(EndpointRule endpointRule);
}