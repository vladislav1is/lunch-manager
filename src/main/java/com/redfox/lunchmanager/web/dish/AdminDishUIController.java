package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.View;
import com.redfox.lunchmanager.to.DishTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/admin/restaurants/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishUIController extends AbstractDishController {

    @Override
    @GetMapping
    public List<DishTo> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @Override
    @GetMapping("/{id}")
    public DishTo get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class) DishTo dish, @PathVariable int restaurantId) {
        if (dish.isNew()) {
            super.create(dish, restaurantId);
        } else {
            super.update(dish, dish.id(), restaurantId);
        }
    }

    @Override
    @GetMapping("/filter")
    public List<DishTo> getBetween(@RequestParam @Nullable LocalDate startDate,
                                   @RequestParam @Nullable LocalDate endDate,
                                   @PathVariable int restaurantId) {
        return super.getBetween(startDate, endDate, restaurantId);
    }
}