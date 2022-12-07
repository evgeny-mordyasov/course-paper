package ru.gold.ordance.course.web.service.authorization.jwt.rule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointPermitConstant.admin;
import static ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointPermitConstant.anonymous;

public enum RolePrivilege {
    ANONYMOUS(anonymous()),
    ADMIN(admin());

    private final EndpointPermit endpointPermit;

    RolePrivilege(EndpointPermit endpointPermit) {
        this.endpointPermit = endpointPermit;
    }

    public static List<RolePrivilege> list() {
        return Arrays.stream(RolePrivilege.values())
                .collect(Collectors.toList());
    }

    public EndpointPermit getEndpointPermit() {
        return endpointPermit;
    }
}