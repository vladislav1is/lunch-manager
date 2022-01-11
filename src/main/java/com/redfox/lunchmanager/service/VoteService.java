package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfDayOrMin;
import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfNextDayOrMax;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int restaurantId) {
        return repository.save(vote, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Vote get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public Vote getByDate(LocalDate localDate, int userId) {
        return repository.getByDate(localDate, userId);
    }

    public List<Vote> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), restaurantId);
    }

    public void update(Vote vote, int restaurantId) {
        checkNotFoundWithId(repository.save(vote, restaurantId), vote.id());
    }

    public Vote getWithRestaurant(int id, int restaurantId) {
        return checkNotFoundWithId(repository.getWithRestaurant(id, restaurantId), id);
    }
}
