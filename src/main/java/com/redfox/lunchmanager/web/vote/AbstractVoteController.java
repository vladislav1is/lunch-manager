package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static com.redfox.lunchmanager.util.Votes.*;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {

    private static final Logger log = getLogger(AbstractVoteController.class);

    @Autowired
    private VoteService service;

    public VoteTo create(VoteTo voteTo, int restaurantId) {
        Vote vote = convertToEntity(voteTo);
        log.info("create {} for restaurant {}", vote, restaurantId);
        checkNew(vote);
        service.create(vote, restaurantId);
        voteTo.setId(vote.id());
        return voteTo;
    }

    public void delete(int id, int restaurantId) {
        log.info("delete vote {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    public VoteTo get(int id, int restaurantId) {
        log.info("get vote {} for restaurant {}", id, restaurantId);
        return convertToDto(service.get(id, restaurantId));
    }

    public VoteTo getByDate(LocalDate localDate) {
        log.info("getByDate {}", localDate);
        int userId = SecurityUtil.authUserId();
        Vote vote = service.getByDate(localDate, userId);
        if (vote != null) {
            return convertToDto(vote);
        } else {
            return null;
        }
    }

    public List<VoteTo> getAll(int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return getTos(service.getAll(restaurantId));
    }

    public List<VoteTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return getTos(service.getBetweenHalfOpen(startDate, endDate, restaurantId));
    }

    public void update(VoteTo voteTo, int id, int restaurantId) {
        Vote vote = convertToEntity(voteTo);
        log.info("update {} with restaurant={}", vote, restaurantId);
        assureIdConsistent(vote, id);
        service.update(vote, restaurantId);
    }
}
