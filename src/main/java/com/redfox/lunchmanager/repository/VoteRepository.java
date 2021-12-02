package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to restaurantId
    Vote save(Vote vote, int restaurantId);

    // false if vote do not belong to restaurantId
    boolean delete(int id, int restaurantId);

    // null if vote do not belong to restaurantId
    Vote get(int id, int restaurantId);

    // null if vote do not belong to userId
    Vote getByDate(LocalDate voteDate, int userId);

    // ORDERED date desc
    List<Vote> getAll(int restaurantId);

    // ORDERED date desc
    List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId);

    default Vote getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}