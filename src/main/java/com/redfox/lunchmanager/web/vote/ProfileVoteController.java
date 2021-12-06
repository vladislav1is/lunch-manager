package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.to.VoteTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
@RequestMapping("/profile/restaurants/{restaurantId}/votes")
public class ProfileVoteController extends AbstractVoteController {

    private final RestaurantService restaurantService;

    public ProfileVoteController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getVotes(@PathVariable int restaurantId, Model model) {
        model.addAttribute("votes", super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "vote-form";
    }

    @PostMapping
    public String updateOrCreate(@PathVariable int restaurantId) {
        var newVote = new VoteTo(null, LocalDate.now());
        VoteTo currentVote = super.getByDate(LocalDate.now());
        if (currentVote != null) {
            super.update(newVote, currentVote.getId(), restaurantId);
        } else {
            super.create(newVote, restaurantId);
        }
        return "redirect:/profile/restaurants";
    }
}
