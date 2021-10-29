package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryDishRepository implements DishRepository {

    private static final AtomicInteger counter = new AtomicInteger(100_000);
    // Map restaurantId -> dishes
    private final Map<Integer, InMemoryBaseRepository<Dish>> dishes = new ConcurrentHashMap<>();

    public void init() {
        save(new Dish("roast pork", 250, LocalDate.now()), restaurant1.getId());
        save(new Dish("fish and chips", 350, LocalDate.now()), restaurant1.getId());
        save(new Dish("roast vegetables", 130, LocalDate.now()), restaurant2.getId());
        save(new Dish("roast turkey", 210, LocalDate.now()), restaurant2.getId());
        save(new Dish("tomato soup", 150, LocalDate.now()), restaurant3.getId());
        save(new Dish("pizza", 180, LocalDate.now()), restaurant3.getId());
        save(new Dish("pasta", 175, LocalDate.now()), restaurant3.getId());
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        Objects.requireNonNull(dish, "dish must not be null");
        InMemoryBaseRepository<Dish> menu = dishes.computeIfAbsent(restaurantId, rid -> new InMemoryBaseRepository<>(counter));
        return menu.save(dish);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        InMemoryBaseRepository<Dish> menu = dishes.get(restaurantId);
        return menu != null && menu.delete(id);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        InMemoryBaseRepository<Dish> menu = dishes.get(restaurantId);
        return menu == null ? null : menu.get(id);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return filterByPredicate(restaurantId, item -> true);
    }

    private List<Dish> filterByPredicate(int restaurantId, Predicate<Dish> filter) {
        InMemoryBaseRepository<Dish> menu = dishes.get(restaurantId);
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
