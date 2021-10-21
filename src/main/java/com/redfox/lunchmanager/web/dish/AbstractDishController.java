package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;

public abstract class AbstractDishController {

    @Autowired
    private DishService service;

    public Dish create(Dish dish, int restaurantId) {
        checkNew(dish);
        return service.create(dish, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        service.delete(id, restaurantId);
    }

    public Dish get(int id, int restaurantId) {
        return service.get(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        return service.getAll(restaurantId);
    }

    public List<Dish> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        return service.getBetweenHalfOpen(startDate, endDate, restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }
}
