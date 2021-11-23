package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishRestController extends AbstractDishController {

    protected static final String REST_URL = "/rest/profile/restaurants/{restaurantId}/dishes";

    @Override
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @GetMapping("/between")
    public List<Dish> getBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PathVariable int restaurantId) {
        return super.getBetween(startDate, endDate, restaurantId);
    }
}