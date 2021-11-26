package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.to.RestaurantTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController extends AbstractRestaurantController {

    @GetMapping
    public String getRestaurants(Model model) {
        model.addAttribute("restaurants", super.getAll());
        return "admin-restaurants";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String id) {
        super.delete(Integer.parseInt(id));
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/update")
    public String update(@RequestParam String id, Model model) {
        var restaurant = super.get(Integer.parseInt(id));
        model.addAttribute("restaurant", restaurant);
        return "restaurant-form";
    }

    @GetMapping("/create")
    public String create(Model model) {
        var restaurant = new RestaurantTo(null, "", null);
        model.addAttribute("restaurant", restaurant);
        return "restaurant-form";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        var restaurant = new RestaurantTo(null, request.getParameter("title"), null);
        if (request.getParameter("id").isEmpty()) {
            super.create(restaurant);
        } else {
            super.update(restaurant, getId(request));
        }
        return "redirect:/admin/restaurants";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
