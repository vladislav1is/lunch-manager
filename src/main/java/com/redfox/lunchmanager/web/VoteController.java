package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.web.restaurant.AbstractRestaurantController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static com.redfox.lunchmanager.util.Votes.canVoteAfter;
import static com.redfox.lunchmanager.util.Votes.deadline;

@ApiIgnore
@Controller
@RequestMapping("/restaurants/{restaurantId}/vote")
public class VoteController extends AbstractRestaurantController {

    private final VoteService service;

    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping
    public String getRestaurant(@PathVariable int restaurantId, Model model) {
        RestaurantTo restaurant = super.getWithUserVote(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("enabled", !canVoteAfter(deadline) || restaurant.getVoteTo() == null);
        return "vote";
    }

    @PostMapping
    public String vote(@PathVariable int restaurantId, @RequestParam String voteId) {
        if (!voteId.isEmpty()) {
            service.updateToday(SecurityUtil.authUserId(), restaurantId, false);
        } else {
            service.createToday(restaurantId, SecurityUtil.authUserId());
        }
        return "redirect:/restaurants";
    }
}
