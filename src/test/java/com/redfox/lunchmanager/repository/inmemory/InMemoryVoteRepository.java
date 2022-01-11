package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.DateTimeUtil.isBetweenHalfOpen;
import static java.time.LocalDate.now;

@Repository
public class InMemoryVoteRepository implements VoteRepository {

    // Map restaurantId -> votes
    private final Map<Integer, InMemoryBaseRepository<Vote>> usersVotes = new ConcurrentHashMap<>();

    {
        save(new Vote(user1, restaurant1, now()), restaurant1.getId());
        save(new Vote(user2, restaurant4, now()), restaurant4.getId());
        save(new Vote(user3, restaurant3, now()), restaurant3.getId());
    }

    @Override
    public Vote save(Vote vote, int restaurantId) {
        Objects.requireNonNull(vote, "vote must not be null");
        var votes = usersVotes.computeIfAbsent(restaurantId, rid -> new InMemoryBaseRepository<>());
        return votes.save(vote);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        var votes = usersVotes.get(restaurantId);
        return votes != null && votes.delete(id);
    }

    @Override
    public Vote get(int id, int restaurantId) {
        var votes = usersVotes.get(restaurantId);
        return votes == null ? null : votes.get(id);
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        Objects.requireNonNull(voteDate, "voteDate must not be null");
        var repositories = usersVotes.values();
        Vote vote = null;
        for (InMemoryBaseRepository<Vote> repository : repositories) {
            vote = repository.getCollection().stream()
                    .filter(v -> v.getRegistered().equals(voteDate) && v.getUser().id() == userId)
                    .findFirst()
                    .orElse(null);
            if (vote != null) {
                break;
            }
        }
        return vote;
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return filterByPredicate(restaurantId, item -> true);
    }

    private List<Vote> filterByPredicate(int restaurantId, Predicate<Vote> filter) {
        var votes = usersVotes.get(restaurantId);
        return votes == null ? Collections.emptyList() :
                votes.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Vote::getRegistered).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return filterByPredicate(restaurantId, item -> isBetweenHalfOpen(item.getRegistered(), startDate, endDate));
    }
}