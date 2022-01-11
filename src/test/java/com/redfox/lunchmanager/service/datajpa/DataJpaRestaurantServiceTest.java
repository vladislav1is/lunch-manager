package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(DATAJPA)
class DataJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
    @Test
    void getWithDishes() {
        Restaurant restaurant = service.getWithDishes(RESTAURANT_ID_1);
        WITH_DISHES_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getWithMealsNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithDishes(RestaurantTestData.NOT_FOUND));
    }
}