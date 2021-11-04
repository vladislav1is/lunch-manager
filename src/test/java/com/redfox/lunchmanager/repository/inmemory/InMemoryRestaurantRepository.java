package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.RestaurantTestData.restaurants;
import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;

@Repository
public class InMemoryRestaurantRepository extends InMemoryBaseRepository<Restaurant> implements RestaurantRepository {

    {
        map.clear();
        restaurants.forEach(restaurant -> map.put(restaurant.getId(), restaurant));
        counter.set(START_SEQ + restaurants.size());
    }

    @Override
    public List<Restaurant> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }
}
