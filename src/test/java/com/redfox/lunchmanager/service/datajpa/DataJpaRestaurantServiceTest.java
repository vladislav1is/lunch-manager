package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.DishTestData;
import com.redfox.lunchmanager.RestaurantTestData;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.DishTestData.dish1;
import static com.redfox.lunchmanager.DishTestData.dish2;
import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(DATAJPA)
class DataJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
    @Test
    void getWithDishes() {
        Restaurant restaurant = service.getWithDishes(RESTAURANT_ID_1);
        RestaurantTestData.MATCHER.assertMatch(restaurant, restaurant1);
        DishTestData.MATCHER.assertMatch(restaurant.getDishes(), dish1, dish2);
    }

    @Test
    void getWithMealsNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithDishes(RestaurantTestData.NOT_FOUND));
    }
}