package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.Restaurants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Users.getTos;

@Controller
public class RootController {

    private final UserService userService;

    private final VoteService voteService;

    private final RestaurantService restaurantService;

    public RootController(UserService userService, RestaurantService restaurantService, VoteService voteService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.voteService = voteService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", getTos(userService.getAll()));
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
    public String editRestaurants(Model model) {
        model.addAttribute("restaurants", Restaurants.getTos(restaurantService.getAll()));
        return "restaurants-editor";
    }

    @PostMapping("/users")
    public String setUser(@RequestParam int userId) {
        SecurityUtil.setAuthUserId(userId);
        return "redirect:/restaurants";
    }
}