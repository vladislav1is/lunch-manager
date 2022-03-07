package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.restaurant.ProfileRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.YAKITORIYA_ID;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.user3;
import static com.redfox.lunchmanager.util.Dishes.convertToDto;
import static com.redfox.lunchmanager.util.Dishes.getTos;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestaurantController.REST_URL + '/' + YAKITORIYA_ID + "/dishes/";

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + YAKITORIYA_ID_1)
                .with(userHttpBasic(user3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(convertToDto(yakitoriya_1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user3)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getDishesForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getTos(yakitoriya_1, yakitoriya_2)));
    }
}