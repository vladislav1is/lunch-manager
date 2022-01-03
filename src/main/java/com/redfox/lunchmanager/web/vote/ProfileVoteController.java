package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.util.Votes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
@RequestMapping("/restaurants/{restaurantId}/votes")
public class ProfileVoteController extends AbstractVoteController {

    private final RestaurantService restaurantService;

    public ProfileVoteController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getVotesForToday(@PathVariable int restaurantId, Model model) {
        model.addAttribute("votes", super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));

        VoteTo voteTo = super.getByDate(LocalDate.now());
        model.addAttribute("voteTo", voteTo);
        model.addAttribute("enabled", Votes.canRevoteBefore(11, 0) || voteTo == null);
        return "vote-form";
    }

    @PostMapping
    public String updateOrCreate(@PathVariable int restaurantId, @RequestParam String voteId) {
        var newVote = new VoteTo(null, LocalDate.now());
        if (!voteId.isEmpty()) {
            super.update(newVote, Integer.parseInt(voteId), restaurantId);
        } else {
            super.create(newVote, restaurantId);
        }
        return "redirect:/restaurants";
    }
}
