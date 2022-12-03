package ru.gold.ordance.course.web.service.authorization.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.base.service.core.ClientService;
import ru.gold.ordance.course.persistence.entity.impl.Client;

import java.util.Collections;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientService service;

    public UserDetailsServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Client client = getEntity(service.findByEmail(email));

        return new UserDetailsImpl(
                client.getEntityId(),
                client.getEmail(),
                client.getPassword(),
                client.getIsActive(),
                Collections.singleton(new SimpleGrantedAuthority(client.getRole().name())));
    }
}
