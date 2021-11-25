package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.to.RestaurantTo;

import java.util.Collection;
import java.util.List;

public class Restaurants {

    public Restaurants() {
    }

    public static RestaurantTo convertToDto(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public static Restaurant convertToEntity(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Restaurants::convertToDto)
                .toList();
    }
}
