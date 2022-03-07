package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Votes.*;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {

    private static final Logger log = getLogger(AbstractVoteController.class);

    @Autowired
    private VoteService service;

    public VoteTo createToday(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("voteToday for restaurant {} with user {}", restaurantId, userId);
        return convertToDto(service.createToday(restaurantId, userId));
    }

    public void updateToday(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("re-voteToday for restaurant {} with user {}", restaurantId, userId);
        service.updateToday(userId, restaurantId, false);
    }

    public void deleteToday() {
        int userId = SecurityUtil.authUserId();
        log.info("deleteToday for user {}", userId);
        service.updateToday(userId, EMPTY, true);
    }

    public void deleteAll(int restaurantId) {
        log.info("deleteAll for restaurant {}", restaurantId);
        service.deleteAll(restaurantId);
    }

    public VoteTo getBy(LocalDate voteDate) {
        log.info("getBy {}", voteDate);
        int userId = SecurityUtil.authUserId();
        Vote vote = service.getBy(voteDate, userId);
        if (vote != null) {
            return convertToDto(vote);
        } else {
            return null;
        }
    }

    public List<VoteTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for userId {}", userId);
        return getTos(service.getAll(userId));
    }

    public int countBy(LocalDate voteDate, int restaurantId) {
        log.info("countBy {} for restaurant {}", voteDate, restaurantId);
        return service.countBy(voteDate, restaurantId);
    }
}
