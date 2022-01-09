package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.service.DishService;
import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import com.redfox.lunchmanager.web.restaurant.AdminRestRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static com.redfox.lunchmanager.DishTestData.*;
import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_2;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.UserTestData.user1;
import static com.redfox.lunchmanager.UserTestData.user3;
import static com.redfox.lunchmanager.util.Dishes.convertToDto;
import static com.redfox.lunchmanager.util.Dishes.getTos;
import static com.redfox.lunchmanager.util.exception.ErrorType.VALIDATION_ERROR;
import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME_AND_DATE;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestRestaurantController.REST_URL + '/' + RESTAURANT_ID_2 + "/dishes/";

    @Autowired
    private DishService dishService;

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
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_ID_3)
                .with(userHttpBasic(user1)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_ID_3, RESTAURANT_ID_2));
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
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID_3)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(dish3)));
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
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(getTos(dish3, dish4)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .with(userHttpBasic(user1))
                .param("startDate", "2021-11-11")
                .param("endDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TO_MATCHER.contentJson(getTos(dish3, dish4)));
    }

    @Test
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endDate=")
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(TO_MATCHER.contentJson(getTos(dish3, dish4)));
    }

    @Test
    void update() throws Exception {
        DishTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_3).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(dishService.get(DISH_ID_3, RESTAURANT_ID_2)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        DishTo newDish = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        DishTo created = TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        TO_MATCHER.assertMatch(created, newDish);
        TO_MATCHER.assertMatch(convertToDto(dishService.get(newId, RESTAURANT_ID_2)), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        DishTo invalid = new DishTo(null, null, 200, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        DishTo invalid = new DishTo(DISH_ID_1, null, 0, LocalDate.now());
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        DishTo invalid = convertToDto(getUpdated());
        invalid.setName("roast turkey");
        invalid.setRegistered(of(2021, Month.NOVEMBER, 11));

        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME_AND_DATE));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        DishTo invalid = convertToDto(getNew());
        invalid.setName("roast turkey");
        invalid.setRegistered(of(2021, Month.NOVEMBER, 11));

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME_AND_DATE));
    }
}