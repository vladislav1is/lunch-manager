package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.util.Restaurants;
import org.junit.jupiter.api.Test;

import static com.redfox.lunchmanager.TestUtil.userAuth;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.yakitoriya;
import static com.redfox.lunchmanager.web.user.UserTestData.admin1;
import static com.redfox.lunchmanager.web.user.UserTestData.user3;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getRestaurants() throws Exception {
        perform(get("/restaurants")
                .with(userAuth(user3)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants.jsp"));
    }

    @Test
    void editRestaurants() throws Exception {
        perform(get("/restaurants/editor")
                .with(userAuth(admin1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants-editor"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants-editor.jsp"));
    }

    @Test
    void getUsers() throws Exception {
        perform(get("/users")
                .with(userAuth(admin1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void unAuth() throws Exception {
        perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getDishesToday() throws Exception {
        perform(get("/restaurants/100004/dishes")
                .with(userAuth(user3)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dishes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/dishes.jsp"))
                .andExpect(model().attribute("restaurant", Restaurants.convertToDto(yakitoriya)));
    }

    @Test
    void editDishes() throws Exception {
        perform(get("/restaurants/100004/dishes/editor")
                .with(userAuth(admin1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dishes-editor"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/dishes-editor.jsp"))
                .andExpect(model().attribute("restaurant", Restaurants.convertToDto(yakitoriya)));
    }
}