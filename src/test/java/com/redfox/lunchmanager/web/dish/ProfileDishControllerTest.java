package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.util.Restaurants;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.DishTestData.TO_MATCHER;
import static com.redfox.lunchmanager.DishTestData.dishes;
import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.util.Dishes.getTos;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileDishControllerTest extends AbstractControllerTest {

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
                                TO_MATCHER.assertMatch(actual, getTos(dishes));
                            }
                        }
                ));
    }
}