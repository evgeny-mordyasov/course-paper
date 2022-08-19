package ru.gold.ordance.course.web.service.web.authorization.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;

import java.util.Collections;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientService service;

    public UserDetailsServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> foundClient = service.findByEmail(email);
        Client client = foundClient.orElseThrow(() -> new UsernameNotFoundException("The user by email not found."));

        return new UserDetailsImpl(
                client.getId(),
                client.getEmail(),
                client.getPassword(),
                client.isActive(),
                Collections.singleton(new SimpleGrantedAuthority(client.getRole().name())));
    }
}
