package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.restaurant.ProfileRestRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.DishTestData.*;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_2;
import static com.redfox.lunchmanager.util.Dishes.convertToDto;
import static com.redfox.lunchmanager.util.Dishes.getTos;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileDishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestRestaurantController.REST_URL + '/' + RESTAURANT_ID_2 + "/dishes/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID_3))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(dish3)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2021-11-11")
                .param("endDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TO_MATCHER.contentJson(getTos(dish3, dish4)));
    }

    @Test
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endDate="))
                .andExpect(status().isOk())
                .andExpect(TO_MATCHER.contentJson(getTos(dish3, dish4)));
    }
}