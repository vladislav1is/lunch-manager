package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/profile/votes")
public class VoteController {

    private static final Logger log = getLogger(VoteController.class);

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    public Vote create(int restaurantId) {
        Vote vote = new Vote(null, null, LocalDate.now());
        log.info("create {}", vote);
        checkNew(vote);
        return voteService.create(vote, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete {}", id);
        voteService.delete(id, restaurantId);
    }

    public Vote get(int id, int restaurantId) {
        log.info("get {}", id);
        return voteService.get(id, restaurantId);
    }

    public Vote getByDate(LocalDate localDate) {
        log.info("getByDate {}", localDate);
        int userId = SecurityUtil.authUserId();
        return voteService.getByDate(localDate, userId);
    }

    public List<Vote> getAll(int restaurantId) {
        log.info("getAll");
        return voteService.getAll(restaurantId);
    }

    public List<Vote> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return voteService.getBetweenHalfOpen(startDate, endDate, restaurantId);
    }

    public void update(int id, int restaurantId) {
        Vote vote = voteService.get(id, restaurantId);
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        voteService.update(vote, restaurantId);
    }
}
