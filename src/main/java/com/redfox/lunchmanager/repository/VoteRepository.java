package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to userId
    Vote save(Vote vote, int userId);

    // false if vote do not belong to userId
    boolean delete(int id, int userId);

    // null if vote do not belong to userId
    Vote get(int id, int userId);

    // null if vote do not belong to userId
    Vote getByDate(LocalDate voteDate, int userId);

    // ORDERED dateTime desc
    List<Vote> getAll(int userId);

    // ORDERED date desc
    List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int userId);
}