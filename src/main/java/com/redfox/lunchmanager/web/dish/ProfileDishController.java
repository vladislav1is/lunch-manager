package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
@RequestMapping("/restaurants/{restaurantId}/dishes")
public class ProfileDishController extends AbstractDishController {

    private final RestaurantService restaurantService;

    public ProfileDishController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getDishesForToday(@PathVariable int restaurantId, Model model) {
        model.addAttribute("dishes", super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "dishes";
    }
}
