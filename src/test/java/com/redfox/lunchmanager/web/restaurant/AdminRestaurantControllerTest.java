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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.admin1;
import static com.redfox.lunchmanager.web.user.UserTestData.user3;
import static com.redfox.lunchmanager.web.vote.VoteTestData.vote1;
import static com.redfox.lunchmanager.util.Restaurants.convertToDto;
import static com.redfox.lunchmanager.util.Restaurants.getTos;
import static com.redfox.lunchmanager.util.exception.ErrorType.VALIDATION_ERROR;
import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

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
        perform(MockMvcRequestBuilders.delete(REST_URL + YAKITORIYA_ID)
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(YAKITORIYA_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + YAKITORIYA_ID)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(convertToDto(yakitoriya)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getTos(restaurants, vote1)));
    }

    @Test
    void update() throws Exception {
        RestaurantTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + MCDONALDS_ID)
                .with(userHttpBasic(admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_TO_MATCHER.assertMatch(convertToDto(restaurantService.get(MCDONALDS_ID)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        RestaurantTo newRestaurant = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        RestaurantTo created = RESTAURANT_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_TO_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_TO_MATCHER.assertMatch(convertToDto(restaurantService.get(newId)), newRestaurant);
    }

    @Test
    void getWithDishesByDate() throws Exception {
        assumeDataJpa();
        perform(MockMvcRequestBuilders.get(REST_URL + DODO_PIZZA_ID + "/with-dishes")
                .with(userHttpBasic(admin1))
                .param("date", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(convertToDto(dodoPizza, dodoPizza.getDishes())));
    }

    @Test
    void createInvalid() throws Exception {
        RestaurantTo invalid = new RestaurantTo(null, null, null, null, 0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(VALIDATION_ERROR.name()));
    }

    @Test
    void updateInvalid() throws Exception {
        RestaurantTo invalid = new RestaurantTo(YAKITORIYA_ID, null, null, null, 0);
        perform(MockMvcRequestBuilders.put(REST_URL + YAKITORIYA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(VALIDATION_ERROR.name()));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        RestaurantTo invalid = convertToDto(getUpdated());
        invalid.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        RestaurantTo invalid = convertToDto(getUpdated());
        invalid.setName(starbucks.getName());

        perform(MockMvcRequestBuilders.put(REST_URL + MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        RestaurantTo invalid = convertToDto(getNew());
        invalid.setName(starbucks.getName());

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }
}