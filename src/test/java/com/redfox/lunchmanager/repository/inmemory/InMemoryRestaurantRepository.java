package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;

@Repository
public class InMemoryRestaurantRepository extends InMemoryBaseRepository<Restaurant> implements RestaurantRepository {

    public void init() {
        map.clear();
        map.put(RESTAURANT_ID_1, restaurant1);
        map.put(RESTAURANT_ID_2, restaurant2);
        map.put(RESTAURANT_ID_3, restaurant3);
        map.put(RESTAURANT_ID_4, restaurant4);
        map.put(RESTAURANT_ID_5, restaurant5);
        counter.set(START_SEQ + 5);
    }

    @Override
    public List<Restaurant> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }
}
