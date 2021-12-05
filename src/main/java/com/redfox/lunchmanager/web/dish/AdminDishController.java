package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.to.DishTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Objects;

import static com.redfox.lunchmanager.util.DateTimeUtil.parseLocalDate;
import static com.redfox.lunchmanager.util.Restaurants.convertToDto;

@Controller
@RequestMapping("/admin/restaurants/{restaurantId}/dishes")
public class AdminDishController extends AbstractDishController {

    private final RestaurantService restaurantService;

    public AdminDishController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getDishes(@PathVariable int restaurantId, Model model) {
        model.addAttribute("dishes", super.getAll(restaurantId));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "admin-dishes";
    }

    @GetMapping("/delete")
    public String delete(@PathVariable int restaurantId, @RequestParam String id) {
        super.delete(Integer.parseInt(id), restaurantId);
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }

    @GetMapping("/update")
    public String update(@PathVariable int restaurantId, @RequestParam String id, Model model) {
        model.addAttribute("dish", super.get(Integer.parseInt(id), restaurantId));
        model.addAttribute("restaurantId", restaurantId);
        return "dish-form";
    }

    @GetMapping("/create")
    public String create(@PathVariable int restaurantId, Model model) {
        var dish = new DishTo(null, "", 0, LocalDate.now());
        model.addAttribute("dish", dish);
        model.addAttribute("restaurantId", restaurantId);
        return "dish-form";
    }

    @PostMapping
    public String updateOrCreate(@PathVariable int restaurantId, HttpServletRequest request) {
        var dish = new DishTo(null, request.getParameter("name"),
                Long.parseLong(request.getParameter("price")),
                LocalDate.parse(request.getParameter("registered")));
        if (request.getParameter("id").isEmpty()) {
            super.create(dish, restaurantId);
        } else {
            super.update(dish, getId(request), restaurantId);
        }
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }

    @GetMapping("/filter")
    public String getBetween(@PathVariable int restaurantId, HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        model.addAttribute("dishes", super.getBetween(startDate, endDate, restaurantId));
        Restaurant restaurant = restaurantService.get(restaurantId);
        model.addAttribute("restaurant", convertToDto(restaurant));
        return "admin-dishes";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
