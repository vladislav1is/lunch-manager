package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.MATCHER;
import static com.redfox.lunchmanager.RestaurantTestData.restaurants;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getRestaurants() throws Exception {
        perform(get("/admin/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin-restaurants.jsp"))
                .andExpect(model().attribute("restaurants",
                        new AssertionMatcher<List<Restaurant>>() {
                            @Override
                            public void assertion(List<Restaurant> actual) throws AssertionError {
                                MATCHER.assertMatch(actual, restaurants);
                            }
                        }
                ));
    }
}