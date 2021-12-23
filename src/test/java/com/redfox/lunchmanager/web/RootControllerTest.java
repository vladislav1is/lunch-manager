package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.DishTestData;
import com.redfox.lunchmanager.RestaurantTestData;
import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.Dishes;
import com.redfox.lunchmanager.util.Restaurants;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.DishTestData.dishes;
import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.RestaurantTestData.restaurants;
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
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants-editor.jsp"));
    }

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void getDishesForToday() throws Exception {
        perform(get("/restaurants/100004/dishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dishes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/dishes.jsp"))
                .andExpect(model().attribute("restaurant", Restaurants.convertToDto(restaurant1)))
                .andExpect(model().attribute("dishes",
                        new AssertionMatcher<List<DishTo>>() {
                            @Override
                            public void assertion(List<DishTo> actual) throws AssertionError {
                                DishTestData.TO_MATCHER.assertMatch(actual, Dishes.getTos(dishes));
                            }
                        }
                ));
    }

    @Test
    void editDishes() throws Exception {
        perform(get("/restaurants/100004/dishes/editor"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dishes-editor"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/dishes-editor.jsp"));
    }
}