package com.redfox.lunchmanager.web.json;

import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.web.user.UserTestData;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.user1);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(convertToDto(UserTestData.user1), "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}