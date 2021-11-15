package com.redfox.lunchmanager.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/restaurants")
public class ProfileRestaurantController extends AbstractRestaurantController {

    @GetMapping
    public String getRestaurants(Model model) {
        model.addAttribute("restaurants", super.getAll());
        return "profile-restaurants";
    }
}
