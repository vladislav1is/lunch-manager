package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.service.AbstractDishServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.restaurant1;

@ActiveProfiles(DATAJPA)
class DataJpaDishServiceTest extends AbstractDishServiceTest {
    @Test
    void getWithRestaurant() {
        Dish dish = service.getWithRestaurant(DISH_ID_1, RESTAURANT_ID_1);
        MATCHER.assertMatch(dish, dish1);
        RestaurantTestData.MATCHER.assertMatch(dish.getRestaurant(), restaurant1);
    }

    @Test
    void getWithRestaurantNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithRestaurant(NOT_FOUND, RESTAURANT_ID_1));
    }
}