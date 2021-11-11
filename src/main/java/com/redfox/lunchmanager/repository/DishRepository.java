package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    // null if updated dish do not belong to restaurantId
    Dish save(Dish dish, int restaurantId);

    // false if dish do not belong to restaurantId
    boolean delete(int id, int restaurantId);

    // null if dish do not belong to restaurantId
    Dish get(int id, int restaurantId);

    // ORDERED date desc
    List<Dish> getAll(int restaurantId);

    // ORDERED date desc
    List<Dish> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId);

    default Dish getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}