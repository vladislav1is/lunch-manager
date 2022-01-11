package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryDishRepository implements DishRepository {

    // Map restaurantId -> dishes
    private final Map<Integer, InMemoryBaseRepository<Dish>> dishes = new ConcurrentHashMap<>();

    {
        save(dish1, restaurant1.getId());
        save(dish2, restaurant1.getId());
        save(dish3, restaurant2.getId());
        save(dish4, restaurant2.getId());
        save(dish5, restaurant3.getId());
        save(dish6, restaurant3.getId());
        save(dish7, restaurant3.getId());
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        Objects.requireNonNull(dish, "dish must not be null");
        var menu = dishes.computeIfAbsent(restaurantId, rid -> new InMemoryBaseRepository<>());
        return menu.save(dish);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        var menu = dishes.get(restaurantId);
        return menu != null && menu.delete(id);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        var menu = dishes.get(restaurantId);
        return menu == null ? null : menu.get(id);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return filterByPredicate(restaurantId, item -> true);
    }

    private List<Dish> filterByPredicate(int restaurantId, Predicate<Dish> filter) {
        var menu = dishes.get(restaurantId);
        return menu == null ? Collections.emptyList() :
                menu.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Dish::getRegistered).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Dish> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return filterByPredicate(restaurantId, item -> isBetweenHalfOpen(item.getRegistered(), startDate, endDate));
    }
}
