package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;

import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFoundWithId;

public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant) {
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }
}
