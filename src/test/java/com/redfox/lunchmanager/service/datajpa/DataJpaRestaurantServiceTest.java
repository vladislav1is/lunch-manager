package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.web.dish.DishTestData;
import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(DATAJPA)
class DataJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
    @Test
    void getWithDishesByDate() {
        Restaurant restaurant = service.getWithDishesByDate(RESTAURANT_ID_1, LocalDate.now());
        WITH_DISHES_MATCHER.assertMatch(restaurant, restaurant1);
        DishTestData.MATCHER.assertMatch(restaurant.getDishes(), DishTestData.dishes);
    }

    @Test
    void getWithDishesNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithDishesByDate(RestaurantTestData.NOT_FOUND, LocalDate.now()));
    }
}