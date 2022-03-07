package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@ApiIgnore
@Controller
public class RootController {

    private final RestaurantService restaurantService;

    public RootController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:restaurants";
    }

    @GetMapping("/restaurants")
    public String getRestaurants() {
        return "restaurants";
    }

    @GetMapping("/restaurants/{restaurantId}/dishes")
    public String getDishesToday(@PathVariable int restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurants/editor")
    public String editRestaurants() {
        return "restaurants-editor";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/restaurants/{restaurantId}/dishes/editor")
    public String editDishes(@PathVariable int restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes-editor";
    }
}