package ru.gold.ordance.course.web.service.web.authorization.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.core.sub.ClientService;

import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientService service;

    public UserDetailsServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = service.getByEmail(email);

        return new UserDetailsImpl(
                client.getEntityId(),
                client.getEmail(),
                client.getPassword(),
                client.getIsActive(),
                Collections.singleton(new SimpleGrantedAuthority(client.getRole().name())));
    }
}
