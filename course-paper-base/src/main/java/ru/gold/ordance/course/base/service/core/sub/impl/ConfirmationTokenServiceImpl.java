package ru.gold.ordance.course.base.service.core.sub.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.sub.ConfirmationTokenService;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;
import ru.gold.ordance.course.persistence.repository.sub.ConfirmationTokenRepository;

import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public ConfirmationToken save(ConfirmationToken entity) {
        return repository.preserve(entity);
    }
}
