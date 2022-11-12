package ru.gold.ordance.course.web.service.web.authorization.jwt.rule;

import ru.gold.ordance.course.base.entity.Role;

public enum EndpointPermit {
    ALL(new EndpointRule[] {
            EndpointRule.all(Endpoint.Authorization.BASE_URL),
            EndpointRule.all(Endpoint.History.BASE_URL)
    }, Role.NONE),
    ADMIN(new EndpointRule[] {
            EndpointRule.post(Endpoint.Classification.BASE_URL),
            EndpointRule.post(Endpoint.File.BASE_URL),
            EndpointRule.post(Endpoint.Language.BASE_URL),
            EndpointRule.put(Endpoint.Classification.BASE_URL),
            EndpointRule.put(Endpoint.Language.BASE_URL),
            EndpointRule.patch(Endpoint.File.BASE_URL),
            EndpointRule.delete(Endpoint.Classification.BASE_URL),
            EndpointRule.delete(Endpoint.File.BASE_URL),
            EndpointRule.delete(Endpoint.Language.BASE_URL),
            EndpointRule.all(Endpoint.Client.BASE_URL),
    }, Role.ADMIN);

    private final EndpointRule[] endpointRules;
    private final Role role;

    EndpointPermit(EndpointRule[] endpointRules, Role role) {
        this.endpointRules = endpointRules;
        this.role = role;
    }

    public EndpointRule[] getRules() {
        return endpointRules;
    }

    public Role getRole() {
        return role;
    }
}