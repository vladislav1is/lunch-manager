package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.to.DishTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishRestController extends AbstractDishController {

    protected static final String REST_URL = "/rest/restaurants/{restaurantId}/dishes";

    @Override
    @GetMapping("/{id}")
    public DishTo get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @GetMapping
    public List<DishTo> getDishesForToday(@PathVariable int restaurantId) {
        return super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId);
    }
}