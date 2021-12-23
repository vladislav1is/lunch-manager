package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.DishService;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.Dishes;
import com.redfox.lunchmanager.util.Restaurants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
public class RootController {

    private final VoteService voteService;

    private final RestaurantService restaurantService;

    private final DishService dishService;

    public RootController(RestaurantService restaurantService, VoteService voteService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
        this.dishService = dishService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

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

    @GetMapping("/restaurants/editor")
    public String editRestaurants() {
        return "restaurants-editor";
    }

    @PostMapping("/users")
    public String setUser(@RequestParam int userId) {
        SecurityUtil.setAuthUserId(userId);
        return "redirect:/restaurants";
    }

    @GetMapping("/restaurants/{restaurantId}/dishes")
    public String getDishesForToday(@PathVariable int restaurantId, Model model) {
        model.addAttribute("dishes", Dishes.getTos(dishService.getBetweenHalfOpen(LocalDate.now(), LocalDate.now(), restaurantId)));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes";
    }

    @GetMapping("/restaurants/{restaurantId}/dishes/editor")
    public String editDishes() {
        return "dishes-editor";
    }
}