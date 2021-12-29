package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.Restaurants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
public class RootController {

    private final VoteService voteService;

    private final RestaurantService restaurantService;

    public RootController(RestaurantService restaurantService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:restaurants";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping("/restaurants")
    public String getRestaurants(Model model) {
        Vote vote = voteService.getByDate(LocalDate.now(), SecurityUtil.authUserId());
        List<RestaurantTo> restaurants;
        if (vote != null) {
            restaurants = Restaurants.getTos(restaurantService.getAll(), vote);
        } else {
            restaurants = Restaurants.getTos(restaurantService.getAll());
        }
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurants/editor")
    public String editRestaurants() {
        return "restaurants-editor";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/restaurants/{restaurantId}/dishes")
    public String getDishesForToday(@PathVariable int restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/restaurants/{restaurantId}/dishes/editor")
    public String editDishes(@PathVariable int restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes-editor";
    }
}