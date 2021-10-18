package com.redfox.lunchmanager.repository.inmemory;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.redfox.lunchmanager.util.Restaurants.*;

public class InMemoryRestaurantRepository extends InMemoryBaseRepository<Restaurant> implements RestaurantRepository {

    private static final AtomicInteger counter = new AtomicInteger(100_000);

    public InMemoryRestaurantRepository() {
        this(counter);
    }

    private InMemoryRestaurantRepository(AtomicInteger counter) {
        super(counter);
    }

    public void init() {
        save(restaurant1);
        save(restaurant2);
        save(restaurant3);
        save(restaurant4);
        save(restaurant5);
    }

    @Override
    public List<Restaurant> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }
}
