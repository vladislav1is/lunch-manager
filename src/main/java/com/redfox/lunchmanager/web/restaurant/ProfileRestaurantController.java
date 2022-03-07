package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.to.RestaurantTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    @Override
    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}/with-dishes")
    public RestaurantTo getWithDishesForToday(@PathVariable int id) {
        return super.getWithDishesBy(LocalDate.now(), id);
    }
}
