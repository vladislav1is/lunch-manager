package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, yakitoriya.getName())));
    }

    @Test
    void delete() {
        service.delete(YAKITORIYA_ID);
        assertThrows(NotFoundException.class, () -> service.get(YAKITORIYA_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(YAKITORIYA_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, yakitoriya);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHER.assertMatch(all, mcdonalds, starbucks, dodoPizza, teremok, yakitoriya);
    }

    @Test
    void update() {
        Restaurant restaurant = getUpdated();
        service.update(restaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(MCDONALDS_ID), getUpdated());
    }

    @Test
    void updateNotFound() {
        Restaurant restaurant = getUpdated();
        restaurant.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(restaurant));
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new Restaurant("  ")));
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new Restaurant("R")));
    }
}
