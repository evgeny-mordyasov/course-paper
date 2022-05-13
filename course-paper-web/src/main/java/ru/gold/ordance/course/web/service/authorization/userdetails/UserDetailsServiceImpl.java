package ru.gold.ordance.course.web.service.authorization.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientService service;

    public UserDetailsServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = service.findByEmail(email);

        return client.map(value ->
                new UserDetailsImpl(
                        value.getId(),
                        value.getEmail(),
                        value.getPassword(),
                        value.isActive(),
                        Collections.singleton(new SimpleGrantedAuthority(value.getRole().name()))))
                .orElse(null);
    }
}
