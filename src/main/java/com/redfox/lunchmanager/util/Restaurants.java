package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.to.RestaurantTo;

import java.util.Collection;
import java.util.List;

public class Restaurants {

    public Restaurants() {
    }

    public static RestaurantTo convertToDto(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), null);
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, List<Dish> dishes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), Dishes.getTos(dishes));
    }

    public static Restaurant convertToEntity(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public static Restaurant convertToEntity(RestaurantTo restaurantTo, List<DishTo> dishes) {
        Restaurant restaurant = new Restaurant(restaurantTo.getId(), restaurantTo.getName());
        restaurant.setDishes(Dishes.getEntities(dishes));
        return restaurant;
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Restaurants::convertToDto)
                .toList();
    }
}
