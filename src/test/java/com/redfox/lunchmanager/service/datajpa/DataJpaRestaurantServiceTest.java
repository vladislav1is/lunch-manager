package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.DishTestData;
import com.redfox.lunchmanager.RestaurantTestData;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.DishTestData.*;
import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.RestaurantTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
    @Test
    public void getWithDishes() {
        Restaurant restaurant = service.getWithDishes(RESTAURANT_ID_1);
        RestaurantTestData.MATCHER.assertMatch(restaurant, restaurant1);
        DishTestData.MATCHER.assertMatch(restaurant.getDishes(), dish1, dish2);
    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithDishes(RestaurantTestData.NOT_FOUND));
    }
}