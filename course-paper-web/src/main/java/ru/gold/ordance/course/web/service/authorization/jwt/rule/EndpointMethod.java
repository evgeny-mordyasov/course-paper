package ru.gold.ordance.course.web.service.authorization.jwt.rule;

import org.springframework.http.HttpMethod;

public class EndpointMethod {
    private final HttpMethod httpMethod;

    public EndpointMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public EndpointMethod() {
        httpMethod = null;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public boolean isAllMethods() {
        return httpMethod == null;
    }
}
