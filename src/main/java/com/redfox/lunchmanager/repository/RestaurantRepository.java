package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    // null if not found, when updated
    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    // ORDERED name
    List<Restaurant> getAll();

    default Restaurant getWithDishesBy(int id, LocalDate localDate) {
        throw new UnsupportedOperationException();
    }
}