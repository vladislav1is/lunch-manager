package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.*;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class VoteController {

    private static final Logger log = getLogger(VoteController.class);

    private final VoteService voteService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public VoteController(VoteService voteService, UserService userService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public Vote create(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        Vote vote = new Vote(userService.get(userId), restaurantService.get(restaurantId), LocalDate.now());
        log.info("create {}", vote);
        checkNew(vote);
        return voteService.create(vote, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
        voteService.delete(id, userId);
    }

    public Vote get(int id) {
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
        return voteService.get(id, userId);
    }

    public Vote getByDate(LocalDate localDate) {
        log.info("getByDate {}", localDate);
        int userId = SecurityUtil.authUserId();
        return checkNotFound(voteService.getByDate(localDate, userId), "localDate=" + localDate);
    }

    public List<Vote> getAll() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        return voteService.getAll(userId);
    }

    public List<Vote> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) for user {}", startDate, endDate, userId);
        return voteService.getBetweenHalfOpen(startDate, endDate, userId);
    }

    public void update(int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        Vote vote = voteService.get(id, userId);
        vote.setRestaurant(restaurantService.get(restaurantId));
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        voteService.update(vote, userId);
    }
}
