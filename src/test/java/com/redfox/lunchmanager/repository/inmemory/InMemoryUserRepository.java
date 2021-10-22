package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.UserTestData.*;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    private static final AtomicInteger counter = new AtomicInteger(100_000);

    public InMemoryUserRepository() {
        this(counter);
    }

    private InMemoryUserRepository(AtomicInteger counter) {
        super(counter);
    }

    public void init() {
        save(user1);
        save(user2);
        save(user3);
    }

    @Override
    public User getByEmail(String email) {
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