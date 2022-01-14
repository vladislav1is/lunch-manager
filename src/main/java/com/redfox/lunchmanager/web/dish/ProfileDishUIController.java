package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.to.DishTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/profile/restaurants/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishUIController extends AbstractDishController {

    @GetMapping
    public List<DishTo> getDishesForToday(@PathVariable int restaurantId) {
        return super.getBetween(LocalDate.now(), LocalDate.now(), restaurantId);
    }
}