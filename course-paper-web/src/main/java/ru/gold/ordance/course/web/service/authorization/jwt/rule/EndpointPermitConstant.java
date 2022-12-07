package ru.gold.ordance.course.web.service.authorization.jwt.rule;

import ru.gold.ordance.course.common.constants.Role;

import java.util.List;

import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.all;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.get;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.post;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.put;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.patch;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule.delete;

public final class EndpointPermitConstant {
    private static final List<EndpointRule> ANONYMOUS_RULES = List.of(
            all(Endpoint.Authorization.BASE_URL + "/**"),
            all(Endpoint.History.BASE_URL + "/**"),
            get(Endpoint.Classification.BASE_URL + "/**"),
            get(Endpoint.File.BASE_URL + "/**")
    );

    private static final List<EndpointRule> ADMIN_RULES = List.of(
            all(Endpoint.Client.BASE_URL + "/**"),
            post(Endpoint.Classification.BASE_URL),
            post(Endpoint.File.BASE_URL),
            post(Endpoint.Language.BASE_URL),
            put(Endpoint.Classification.BASE_URL),
            put(Endpoint.Language.BASE_URL),
            patch(Endpoint.File.BASE_URL),
            delete(Endpoint.Classification.BASE_URL),
            delete(Endpoint.File.BASE_URL),
            delete(Endpoint.Language.BASE_URL)
    );

    private EndpointPermitConstant() {
    }

    public static EndpointPermit anonymous() {
        return new EndpointPermit(ANONYMOUS_RULES, Role.ANONYMOUS);
    }

    public static EndpointPermit admin() {
        return new EndpointPermit(ADMIN_RULES, Role.ADMIN);
    }
}
