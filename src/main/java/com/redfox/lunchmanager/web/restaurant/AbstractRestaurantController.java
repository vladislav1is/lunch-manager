package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public Restaurant get(int id) {
        return service.get(id);
    }

    public List<Restaurant> getAll() {
        return service.getAll();
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }
}
