package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfDayOrMin;
import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfNextDayOrMax;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFound;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFoundWithId;

public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int userId) {
        return repository.save(vote, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Vote getByDate(LocalDate localDate, int userId) {
        return checkNotFound(repository.getByDate(localDate, userId), "localDate=" + localDate);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public void update(Vote vote, int userId) {
        checkNotFoundWithId(repository.save(vote, userId), vote.getId());
    }
}
