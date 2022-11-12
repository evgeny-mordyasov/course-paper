package ru.gold.ordance.course.web.service.web.authorization.jwt.rule;

public interface Authority {
    void apply(EndpointRule endpointRule);
}