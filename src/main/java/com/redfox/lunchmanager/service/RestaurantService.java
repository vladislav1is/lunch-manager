package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.validation.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant getWithDishesBy(int id, LocalDate localDate) {
        return checkNotFoundWithId(repository.getWithDishesBy(id, localDate), id);
    }
}
