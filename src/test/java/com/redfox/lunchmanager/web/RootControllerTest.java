package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.RestaurantTestData;
import com.redfox.lunchmanager.UserTestData;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.Restaurants;
import com.redfox.lunchmanager.util.Users;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.restaurants;
import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.VoteTestData.vote4;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getRestaurants() throws Exception {
        perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants.jsp"))
                .andExpect(model().attribute("restaurants",
                        new AssertionMatcher<List<RestaurantTo>>() {
                            @Override
                            public void assertion(List<RestaurantTo> actual) throws AssertionError {
                                RestaurantTestData.TO_MATCHER.assertMatch(actual, Restaurants.getTos(restaurants, vote4));
                            }
                        }
                ));
    }

    @Test
    void editRestaurants() throws Exception {
        perform(get("/restaurants/editor"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants-editor"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants-editor.jsp"))
                .andExpect(model().attribute("restaurants",
                        new AssertionMatcher<List<RestaurantTo>>() {
                            @Override
                            public void assertion(List<RestaurantTo> actual) throws AssertionError {
                                RestaurantTestData.TO_MATCHER.assertMatch(actual, Restaurants.getTos(restaurants));
                            }
                        }
                ));
    }

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<UserTo>>() {
                            @Override
                            public void assertion(List<UserTo> actual) throws AssertionError {
                                UserTestData.TO_MATCHER.assertMatch(actual, Users.getTos(user1, user2, user3));
                            }
                        }
                ));
    }
}