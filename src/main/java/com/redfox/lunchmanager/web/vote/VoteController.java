package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.*;

public class VoteController {

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
        checkNew(vote);
        return voteService.create(vote, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        voteService.delete(id, userId);
    }

    public Vote get(int id) {
        int userId = SecurityUtil.authUserId();
        return voteService.get(id, userId);
    }

    public Vote getByDate(LocalDate localDate) {
        int userId = SecurityUtil.authUserId();
        return checkNotFound(voteService.getByDate(localDate, userId), "localDate=" + localDate);
    }

    public List<Vote> getAll() {
        int userId = SecurityUtil.authUserId();
        return voteService.getAll(userId);
    }

    public List<Vote> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        int userId = SecurityUtil.authUserId();
        return voteService.getBetweenHalfOpen(startDate, endDate, userId);
    }

    public void update(int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        Vote vote = voteService.get(id, userId);
        assureIdConsistent(vote, id);
        vote.setRestaurant(restaurantService.get(restaurantId));
        voteService.update(vote, userId);
    }
}
