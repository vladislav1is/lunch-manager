package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfDayOrMin;
import static com.redfox.lunchmanager.util.DateTimeUtil.atStartOfNextDayOrMax;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish, int restaurantId) {
        return repository.save(dish, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Dish> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }
}
