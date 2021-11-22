package com.redfox.lunchmanager.web.dish;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/profile/restaurants/{restaurantId}/dishes")
public class ProfileDishController extends AbstractDishController {

    @GetMapping
    public String getDishes(@PathVariable int restaurantId, Model model) {
        model.addAttribute("dishes", super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId));
        model.addAttribute("restaurantId", restaurantId);
        return "profile-dishes";
    }
}
