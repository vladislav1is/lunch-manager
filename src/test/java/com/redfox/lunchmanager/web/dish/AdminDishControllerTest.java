package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.DishTestData.MATCHER;
import static com.redfox.lunchmanager.DishTestData.dishes;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminDishControllerTest extends AbstractControllerTest {

    @Test
    void getDishes() throws Exception {
        perform(get("/admin/restaurants/100004/dishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-dishes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin-dishes.jsp"))
                .andExpect(model().attribute("restaurantId",
                        new AssertionMatcher<Integer>() {
                            @Override
                            public void assertion(Integer actual) throws AssertionError {
                                Assertions.assertThat(actual).isEqualTo(RESTAURANT_ID_1);
                            }
                        }))
                .andExpect(model().attribute("dishes",
                        new AssertionMatcher<List<Dish>>() {
                            @Override
                            public void assertion(List<Dish> actual) throws AssertionError {
                                MATCHER.assertMatch(actual, dishes);
                            }
                        }
                ));
    }
}