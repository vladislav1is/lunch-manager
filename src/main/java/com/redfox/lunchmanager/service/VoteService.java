package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.util.exception.DataConflictException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.redfox.lunchmanager.util.DateTimeUtil.atDayOrNow;
import static com.redfox.lunchmanager.util.Votes.canVoteAfter;
import static com.redfox.lunchmanager.util.Votes.deadline;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.checkNotFoundWithId;
import static java.util.Objects.requireNonNull;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    @Modifying
    public Vote createToday(int restaurantId, int userId) {
        LocalDate now = LocalDate.now();
        Optional<Vote> dbVote = Optional.ofNullable(repository.getBy(now, userId));
        dbVote.ifPresent(v -> {
            throw new DataConflictException("Already voted today");
        });
        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setRestaurantId(restaurantId);
        vote.setVoteDate(now);
        return repository.save(vote, restaurantId);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    @Modifying
    public void updateToday(int userId, int restaurantId, boolean deleteVote) {
        if (canVoteAfter(deadline)) {
            throw new DataConflictException("Deadline for change vote has passed");
        }
        Optional<Vote> dbVote = Optional.ofNullable(repository.getBy(LocalDate.now(), userId));
        Vote vote = dbVote.orElseThrow(() -> new DataConflictException("Have not voted today"));
        if (deleteVote) {
            repository.delete(vote.getId(), vote.getRestaurantId());
        } else {
            vote.setRestaurantId(restaurantId);
            repository.save(vote, restaurantId);
        }
    }

    @CacheEvict(value = "votes", allEntries = true)
    public void deleteAll(int restaurantId) {
        checkNotFoundWithId(repository.deleteAllBy(restaurantId), restaurantId);
    }

    public Vote getBy(LocalDate voteDate, int userId) {
        requireNonNull(voteDate, "voteDate must not be null");
        return repository.getBy(atDayOrNow(voteDate), userId);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Cacheable("votes")
    public int countBy(LocalDate voteDate, int restaurantId) {
        return repository.countBy(atDayOrNow(voteDate), restaurantId);
    }
}
