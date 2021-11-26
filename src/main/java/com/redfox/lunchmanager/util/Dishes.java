package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.to.DishTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Dishes {

    public Dishes() {
    }

    public static DishTo convertToDto(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice(), dish.getRegistered());
    }

    public static Dish convertToEntity(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), dishTo.getRegistered());
    }

    public static List<DishTo> getTos(Collection<Dish> dishes) {
        return dishes.stream()
                .map(Dishes::convertToDto)
                .toList();
    }

    public static List<DishTo> getTos(Dish... dishes) {
        return Arrays.stream(dishes)
                .map(Dishes::convertToDto)
                .toList();
    }

    public static List<Dish> getEntities(Collection<DishTo> dishes) {
        return dishes.stream()
                .map(Dishes::convertToEntity)
                .toList();
    }
}
