package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.junit.Test;

import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProfileRestaurantControllerTest extends AbstractControllerTest {

    @Test
    public void getRestaurants() throws Exception {
        perform(get("/profile/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("profile-restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile-restaurants.jsp"))
                .andExpect(model().attribute("restaurants", hasSize(5)))
                .andExpect(model().attribute("restaurants", hasItem(
                        allOf(
                                hasProperty("id", is(RESTAURANT_ID_1)),
                                hasProperty("name", is(restaurant1.getName()))
                        )
                )));
    }
}