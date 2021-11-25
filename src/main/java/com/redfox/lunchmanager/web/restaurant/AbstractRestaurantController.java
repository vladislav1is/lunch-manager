package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.to.RestaurantTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.redfox.lunchmanager.util.Restaurants.*;
import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractRestaurantController {

    private static final Logger log = getLogger(AbstractRestaurantController.class);

    @Autowired
    private RestaurantService service;

    public RestaurantTo create(RestaurantTo restaurantTo) {
        Restaurant restaurant = convertToEntity(restaurantTo);
        log.info("create {}", restaurant);
        checkNew(restaurant);
        service.create(restaurant);
        restaurantTo.setId(restaurant.id());
        return restaurantTo;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public RestaurantTo get(int id) {
        log.info("get {}", id);
        return convertToDto(service.get(id));
    }

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll());
    }

    public void update(RestaurantTo restaurantTo, int id) {
        Restaurant restaurant = convertToEntity(restaurantTo);
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }
}
