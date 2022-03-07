package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to restaurantId
    Vote save(Vote vote, int restaurantId);

    // false if vote do not belong to restaurantId
    boolean delete(int id, int restaurantId);

    // false if no data
    boolean deleteAllBy(int restaurantId);

    // null if vote do not belong to userId
    Vote getBy(LocalDate voteDate, int userId);

    // ORDERED date desc
    List<Vote> getAll(int userId);

    // null if no data
    int countBy(LocalDate voteDate, int restaurantId);
}