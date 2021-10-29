package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.UserTestData.*;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public static final int START_SEQ = 100_000;
    private static final AtomicInteger counter = new AtomicInteger(START_SEQ);

    public InMemoryUserRepository() {
        this(counter);
    }

    private InMemoryUserRepository(AtomicInteger counter) {
        super(counter);
    }

    public void init() {
        map.clear();
        map.put(USER_ID_1, user1);
        map.put(USER_ID_2, user2);
        map.put(USER_ID_3, user3);
        counter.set(START_SEQ + 3);
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