package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Client;

import java.util.Optional;

public interface ClientService extends AbstractService<Client> {
    Optional<Client> findByEmail(String email);
}
