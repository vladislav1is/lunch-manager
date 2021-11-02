package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.util.DateTimeUtil.isBetweenHalfOpen;
import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.UserTestData.*;

@Repository
public class InMemoryVoteRepository implements VoteRepository {

    // Map userId -> votes
    private final Map<Integer, InMemoryBaseRepository<Vote>> usersVotes = new ConcurrentHashMap<>();

    public void init() {
        save(new Vote(user1, restaurant1, LocalDate.now()), user1.getId());
        save(new Vote(user2, restaurant4, LocalDate.now()), user2.getId());
        save(new Vote(user3, restaurant3, LocalDate.now()), user3.getId());
    }

    @Override
    public Vote save(Vote vote, int userId) {
        Objects.requireNonNull(vote, "vote must not be null");
        InMemoryBaseRepository<Vote> votes = usersVotes.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return votes.save(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Vote> votes = usersVotes.get(userId);
        return votes != null && votes.delete(id);
    }

    @Override
    public Vote get(int id, int userId) {
        InMemoryBaseRepository<Vote> votes = usersVotes.get(userId);
        return votes == null ? null : votes.get(id);
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        Objects.requireNonNull(voteDate, "voteDate must not be null");
        InMemoryBaseRepository<Vote> votes = usersVotes.get(userId);
        return votes == null ? null : votes.getCollection().stream()
                .filter(vote -> voteDate.equals(vote.getRegistered()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return filterByPredicate(userId, item -> true);
    }

    private List<Vote> filterByPredicate(int userId, Predicate<Vote> filter) {
        InMemoryBaseRepository<Vote> votes = usersVotes.get(userId);
        return votes == null ? Collections.emptyList() :
                votes.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Vote::getRegistered).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int userId) {
        return filterByPredicate(userId, item -> isBetweenHalfOpen(item.getRegistered(), startDate, endDate));
    }
}