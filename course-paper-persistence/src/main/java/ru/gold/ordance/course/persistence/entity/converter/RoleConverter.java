package ru.gold.ordance.course.persistence.entity.converter;

import ru.gold.ordance.course.common.constants.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.springframework.util.StringUtils.hasText;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }

        if (role.equals(Role.ANONYMOUS)) {
            throw new IllegalArgumentException("Client role can't be ANONYMOUS.");
        }

        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        if (!hasText(role)) {
            return null;
        }

        return Role.valueOf(role);
    }
}
