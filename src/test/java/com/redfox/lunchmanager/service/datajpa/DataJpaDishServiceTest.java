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
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.YAKITORIYA_ID;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.yakitoriya;

@ActiveProfiles(DATAJPA)
class DataJpaDishServiceTest extends AbstractDishServiceTest {
    @Test
    void getWithRestaurant() {
        Dish dish = service.getWithRestaurant(YAKITORIYA_ID_1, YAKITORIYA_ID);
        DISH_MATCHER.assertMatch(dish, yakitoriya_1);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(dish.getRestaurant(), yakitoriya);
    }

    @Test
    void getWithRestaurantNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithRestaurant(NOT_FOUND, YAKITORIYA_ID));
    }
}