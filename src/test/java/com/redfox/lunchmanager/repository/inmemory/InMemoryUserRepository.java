package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    private static final Logger log = getLogger(InMemoryUserRepository.class);

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    public void init() {
        map.clear();
        users.forEach(user -> map.put(user.getId(), user));
        counter.set(START_SEQ + users.size());
    }

    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }
}