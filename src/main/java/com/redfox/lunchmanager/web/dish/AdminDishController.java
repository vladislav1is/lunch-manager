package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;

import java.time.LocalDate;
import java.util.List;

public class AdminDishController extends AbstractDishController {

    @Override
    public Dish create(Dish dish, int restaurantId) {
        return super.create(dish, restaurantId);
    }

    @Override
    public void delete(int id, int restaurantId) {
        super.delete(id, restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return super.getAll(restaurantId);
    }

    @Override
    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return super.getBetween(startDate, endDate, restaurantId);
    }

    @Override
    public void update(Dish dish, int id, int restaurantId) {
        super.update(dish, id, restaurantId);
    }
}
