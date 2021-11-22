package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static com.redfox.lunchmanager.DishTestData.*;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_2;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractDishServiceTest extends AbstractServiceTest {
    @Autowired
    protected DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT_ID_1);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(service.get(newId, RESTAURANT_ID_1), newDish);
    }

    @Test
    void duplicateCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Dish(dish1.getName(), dish1.getPrice(), dish1.getRegistered()), RESTAURANT_ID_1));
    }

    @Test
    void delete() {
        service.delete(DISH_ID_1, RESTAURANT_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_ID_1));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_ID_1, RESTAURANT_ID_2));
    }

    @Test
    void get() {
        Dish actual = service.get(DISH_ID_1, RESTAURANT_ID_1);
        MATCHER.assertMatch(actual, dish1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1, RESTAURANT_ID_2));
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(RESTAURANT_ID_1), dishes);
    }

    @Test
    void getBetweenHalfOpen() {
        MATCHER.assertMatch(service.getBetweenHalfOpen(LocalDate.now(), LocalDate.now(), RESTAURANT_ID_1), dish1, dish2);
    }

    @Test
    void getBetweenWithNullDates() {
        MATCHER.assertMatch(service.getBetweenHalfOpen(null, null, RESTAURANT_ID_1), dishes);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT_ID_2);
        MATCHER.assertMatch(service.get(DISH_ID_3, RESTAURANT_ID_2), getUpdated());
    }

    @Test
    void updateNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(getUpdated(), RESTAURANT_ID_1));
        Assertions.assertEquals("Not found entity with id=" + DISH_ID_3, exception.getMessage());
        MATCHER.assertMatch(service.get(DISH_ID_3, RESTAURANT_ID_2), dish3);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("", 1000, LocalDate.now()), RESTAURANT_ID_1));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 9, LocalDate.now()), RESTAURANT_ID_1));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 10001, LocalDate.now()), RESTAURANT_ID_1));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 1050, null), RESTAURANT_ID_1));
    }
}