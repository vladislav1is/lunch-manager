package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.to.RestaurantTo;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public class Restaurants {

    public Restaurants() {
    }

    public static RestaurantTo convertToDto(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), null, null, 0);
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, Integer votes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), null, null, votes);
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, List<Dish> dishes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), null, Dishes.getTos(dishes), 0);
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, @NonNull Vote vote) {
        Integer restaurantId = restaurant.getId();
        if (restaurantId.equals(vote.getRestaurant().getId())) {
            return new RestaurantTo(restaurantId, restaurant.getName(), Votes.convertToDto(vote), null, 0);
        } else {
            return new RestaurantTo(restaurantId, restaurant.getName(), null, null, 0);
        }
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, Vote vote, Integer votes) {
        Integer restaurantId = restaurant.getId();
        return new RestaurantTo(restaurantId, restaurant.getName(), Votes.convertToDto(vote), null, votes);
    }

    public static RestaurantTo convertToDto(Restaurant restaurant, @NonNull Vote vote, List<Dish> dishes) {
        Integer restaurantId = restaurant.getId();
        if (restaurantId.equals(vote.getRestaurant().getId())) {
            return new RestaurantTo(restaurantId, restaurant.getName(), Votes.convertToDto(vote), Dishes.getTos(dishes), 0);
        } else {
            return new RestaurantTo(restaurantId, restaurant.getName(), null, Dishes.getTos(dishes), 0);
        }
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

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants, Vote vote) {
        return restaurants.stream()
                .map(restaurant -> convertToDto(restaurant, vote))
                .toList();
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants, Vote vote, Integer votes) {
        return restaurants.stream()
                .map(restaurant -> convertToDto(restaurant, vote, votes))
                .toList();
    }
}
