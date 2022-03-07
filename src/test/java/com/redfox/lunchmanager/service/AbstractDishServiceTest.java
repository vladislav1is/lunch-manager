package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.YAKITORIYA_ID;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.DODO_PIZZA_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractDishServiceTest extends AbstractServiceTest {
    @Autowired
    protected DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew(), YAKITORIYA_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, YAKITORIYA_ID), newDish);
    }

    @Test
    void duplicateCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Dish(yakitoriya_1.getName(), yakitoriya_1.getPrice(), yakitoriya_1.getRegistered()), YAKITORIYA_ID));
    }

    @Test
    void delete() {
        service.delete(YAKITORIYA_ID_1, YAKITORIYA_ID);
        assertThrows(NotFoundException.class, () -> service.get(YAKITORIYA_ID_1, YAKITORIYA_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, YAKITORIYA_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(YAKITORIYA_ID_1, DODO_PIZZA_ID));
    }

    @Test
    void get() {
        Dish actual = service.get(YAKITORIYA_ID_1, YAKITORIYA_ID);
        DISH_MATCHER.assertMatch(actual, yakitoriya_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, YAKITORIYA_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(YAKITORIYA_ID_1, DODO_PIZZA_ID));
    }

    @Test
    void getAll() {
        DISH_MATCHER.assertMatch(service.getAll(YAKITORIYA_ID), dishes);
    }

    @Test
    void getBetweenHalfOpen() {
        DISH_MATCHER.assertMatch(service.getBetweenBy(LocalDate.now(), LocalDate.now(), YAKITORIYA_ID), yakitoriya_1, yakitoriya_2);
    }

    @Test
    void getBetweenWithNullDates() {
        DISH_MATCHER.assertMatch(service.getBetweenBy(null, null, YAKITORIYA_ID), dishes);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, DODO_PIZZA_ID);
        DISH_MATCHER.assertMatch(service.get(DODO_PIZZA_ID_1, DODO_PIZZA_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(getUpdated(), YAKITORIYA_ID));
        Assertions.assertEquals("Not found entity with id=" + DODO_PIZZA_ID_1, exception.getMessage());
        DISH_MATCHER.assertMatch(service.get(DODO_PIZZA_ID_1, DODO_PIZZA_ID), dodoPizza_1);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("", 1000, LocalDate.now()), YAKITORIYA_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 9, LocalDate.now()), YAKITORIYA_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 100001, LocalDate.now()), YAKITORIYA_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Dish("Name", 1050, null), YAKITORIYA_ID));
    }
}