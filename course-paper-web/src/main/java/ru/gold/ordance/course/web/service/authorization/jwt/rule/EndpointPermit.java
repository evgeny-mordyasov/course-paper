package ru.gold.ordance.course.web.service.authorization.jwt.rule;

import ru.gold.ordance.course.common.constants.Role;

import java.util.List;

public class EndpointPermit {
    private final List<EndpointRule> endpointRules;
    private final Role role;

    public EndpointPermit(List<EndpointRule> endpointRules, Role role) {
        this.endpointRules = endpointRules;
        this.role = role;
    }

    public List<EndpointRule> getEndpointRules() {
        return endpointRules;
    }

    public Role getRole() {
        return role;
    }
}