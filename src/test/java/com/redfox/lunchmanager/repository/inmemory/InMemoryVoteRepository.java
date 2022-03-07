package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static java.time.LocalDate.now;

@Repository
public class InMemoryVoteRepository implements VoteRepository {

    // Map restaurantId -> votes
    private final Map<Integer, InMemoryBaseRepository<Vote>> restaurantVotes = new ConcurrentHashMap<>();

    {
        save(new Vote(admin1, yakitoriya, now()), yakitoriya.getId());
        save(new Vote(admin2, teremok, now()), teremok.getId());
        save(new Vote(user3, mcdonalds, now()), mcdonalds.getId());
    }

    @Override
    public Vote save(Vote vote, int restaurantId) {
        Objects.requireNonNull(vote, "vote must not be null");
        var votes = restaurantVotes.computeIfAbsent(restaurantId, rid -> new InMemoryBaseRepository<>());
        return votes.save(vote);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        var votes = restaurantVotes.get(restaurantId);
        return votes != null && votes.delete(id);
    }

    @Override
    public boolean deleteAllBy(int restaurantId) {
        return restaurantVotes.remove(restaurantId) != null;
    }

    public Vote getBy(LocalDate voteDate, int userId) {
        Vote vote = null;
        for (InMemoryBaseRepository<Vote> repository : restaurantVotes.values()) {
            vote = repository.getCollection().stream()
                    .filter(v -> v.getVoteDate().equals(voteDate) && v.getUser().id() == userId)
                    .findFirst()
                    .orElse(null);
            if (Objects.nonNull(vote)) {
                break;
            }
        }
        return vote;
    }

    @Override
    public List<Vote> getAll(int userId) {
        return filterByPredicate(userId);
    }

    private List<Vote> filterByPredicate(int userId) {
        List<Vote> votes = new ArrayList<>();
        for (InMemoryBaseRepository<Vote> repository : restaurantVotes.values()) {
            votes.addAll(repository.getCollection().stream()
                    .filter(v -> v.getUser().id() == userId)
                    .toList());
        }
        return votes;
    }

    @Override
    public int countBy(LocalDate voteDate, int restaurantId) {
        var votes = restaurantVotes.computeIfAbsent(restaurantId, rid -> new InMemoryBaseRepository<>());
        return votes.getCollection().size();
    }
}