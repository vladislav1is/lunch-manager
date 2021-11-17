package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static org.junit.Assert.assertThrows;

public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Test
    public void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    public void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "October")));
    }

    @Test
    public void delete() {
        service.delete(RESTAURANT_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID_1));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID_1);
        MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getAll() {
        List<Restaurant> all = service.getAll();
        MATCHER.assertMatch(all, restaurant1, restaurant5, restaurant4, restaurant3, restaurant2);
    }

    @Test
    public void update() {
        Restaurant restaurant = getUpdated();
        service.update(restaurant);
        MATCHER.assertMatch(service.get(RESTAURANT_ID_3), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Restaurant restaurant = getUpdated();
        restaurant.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(restaurant));
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new Restaurant("  ")));
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new Restaurant("R")));
    }
}
