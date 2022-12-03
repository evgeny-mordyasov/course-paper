package ru.gold.ordance.course.web.service.authorization.jwt.rule;

import org.springframework.http.HttpMethod;

public class EndpointRule {
    private final EndpointMethod endpointMethod;
    private final String url;

    public EndpointRule(EndpointMethod endpointMethod, String url) {
        this.endpointMethod = endpointMethod;
        this.url = url;
    }

    public static EndpointRule all(String url) {
        return new EndpointRule(new EndpointMethod(), url + "/**");
    }

    public static EndpointRule post(String url) {
        return new EndpointRule(new EndpointMethod(HttpMethod.POST), url);
    }

    public static EndpointRule put(String url) {
        return new EndpointRule(new EndpointMethod(HttpMethod.PUT), url);
    }

    public static EndpointRule patch(String url) {
        return new EndpointRule(new EndpointMethod(HttpMethod.PATCH), url);
    }

    public static EndpointRule delete(String url) {
        return new EndpointRule(new EndpointMethod(HttpMethod.DELETE), url);
    }

    public boolean isAllMethods() {
        return endpointMethod.isAllMethods();
    }

    public HttpMethod getHttpMethod() {
        return endpointMethod.getHttpMethod();
    }

    public String getUrl() {
        return url;
    }
}

