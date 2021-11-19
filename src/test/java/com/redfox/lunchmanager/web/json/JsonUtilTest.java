package com.redfox.lunchmanager.web.json;

import com.redfox.lunchmanager.RestaurantTestData;
import com.redfox.lunchmanager.model.Restaurant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.RestaurantTestData.restaurants;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(restaurant1);
        System.out.println(json);
        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
        MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(restaurants);
        System.out.println(json);
        List<Restaurant> restaurants = JsonUtil.readValues(json, Restaurant.class);
        MATCHER.assertMatch(restaurants, RestaurantTestData.restaurants);
    }
}