package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.*;
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
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "October")));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID_1);
        MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        MATCHER.assertMatch(all, restaurant1, restaurant5, restaurant4, restaurant3, restaurant2);
    }

    @Test
    void update() {
        Restaurant restaurant = getUpdated();
        service.update(restaurant);
        MATCHER.assertMatch(service.get(RESTAURANT_ID_3), getUpdated());
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
