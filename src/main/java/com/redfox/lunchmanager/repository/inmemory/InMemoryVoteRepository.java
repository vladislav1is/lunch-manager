package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.util.Restaurants.*;
import static com.redfox.lunchmanager.util.Users.*;
import static com.redfox.lunchmanager.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryVoteRepository implements VoteRepository {

    private static final AtomicInteger counter = new AtomicInteger(100_000);
    // Map userId -> votes
    private final Map<Integer, InMemoryBaseRepository<Vote>> usersVotes = new ConcurrentHashMap<>();

    public void init() {
        save(new Vote(user1, restaurant1, LocalDate.now()), user1.getId());
        save(new Vote(user2, restaurant4, LocalDate.now()), user2.getId());
        save(new Vote(user3, restaurant3, LocalDate.now()), user3.getId());
    }

    @Override
    public Vote save(Vote vote, int userId) {
        InMemoryBaseRepository<Vote> votes = usersVotes.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>(counter));
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
        InMemoryBaseRepository<Vote> votes = usersVotes.get(userId);
        return votes == null ? null : votes.getCollection().stream()
                .filter(vote -> voteDate.equals(vote.getVoteDate()))
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
                        .sorted(Comparator.comparing(Vote::getVoteDate).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int userId) {
        return filterByPredicate(userId, item -> isBetweenHalfOpen(item.getVoteDate(), startDate, endDate));
    }
}