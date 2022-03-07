package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.DateTimeUtil.atDayOrMin;
import static com.redfox.lunchmanager.util.DateTimeUtil.atNextDayOrMax;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        return repository.save(dish, restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Dish> getBetweenBy(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return repository.getBetweenBy(atDayOrMin(startDate), atNextDayOrMax(endDate), restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.id());
    }

    public Dish getWithRestaurant(int id, int restaurantId) {
        return checkNotFoundWithId(repository.getWithRestaurant(id, restaurantId), id);
    }
}
