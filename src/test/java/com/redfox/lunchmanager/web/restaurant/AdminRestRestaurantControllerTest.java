package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.UserTestData.user1;
import static com.redfox.lunchmanager.UserTestData.user3;
import static com.redfox.lunchmanager.VoteTestData.vote1;
import static com.redfox.lunchmanager.util.Restaurants.convertToDto;
import static com.redfox.lunchmanager.util.Restaurants.getTos;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestRestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user3)))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_1)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID_1));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_1)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(restaurant1, vote1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(getTos(restaurants, vote1)));
    }

    @Test
    void update() throws Exception {
        RestaurantTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_3)
                .with(userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(restaurantService.get(RESTAURANT_ID_3)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        RestaurantTo newRestaurant = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        RestaurantTo created = TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newRestaurant.setId(newId);
        TO_MATCHER.assertMatch(created, newRestaurant);
        TO_MATCHER.assertMatch(convertToDto(restaurantService.get(newId)), newRestaurant);
    }

    @Test
    void getWithDishes() throws Exception {
        assumeDataJpa();
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_1 + "/with-dishes")
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(restaurant1, vote1, restaurant1.getDishes())));
    }
}