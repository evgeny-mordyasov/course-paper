package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;
import ru.gold.ordance.course.persistence.repository.sub.ConfirmationTokenRepository;

import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenService(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    public Optional<ConfirmationToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    public ConfirmationToken save(ConfirmationToken obj) {
        return repository.preserve(obj);
    }
}
