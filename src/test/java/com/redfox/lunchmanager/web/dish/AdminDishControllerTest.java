package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.service.DishService;
import com.redfox.lunchmanager.to.DishTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import com.redfox.lunchmanager.web.restaurant.AdminRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.DODO_PIZZA_ID;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.admin1;
import static com.redfox.lunchmanager.web.user.UserTestData.user3;
import static com.redfox.lunchmanager.util.Dishes.convertToDto;
import static com.redfox.lunchmanager.util.Dishes.getTos;
import static com.redfox.lunchmanager.util.exception.ErrorType.VALIDATION_ERROR;
import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME_AND_DATE;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/' + DODO_PIZZA_ID + "/dishes/";

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
        perform(MockMvcRequestBuilders.delete(REST_URL + DODO_PIZZA_ID_1)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(DODO_PIZZA_ID_1, DODO_PIZZA_ID));
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
        perform(MockMvcRequestBuilders.get(REST_URL + DODO_PIZZA_ID_1)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(convertToDto(dodoPizza_1)));
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
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getTos(dodoPizza_1, dodoPizza_2)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .with(userHttpBasic(admin1))
                .param("startDate", "2021-11-11")
                .param("endDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DISH_TO_MATCHER.contentJson(getTos(dodoPizza_1, dodoPizza_2)));
    }

    @Test
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endDate=")
                .with(userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(getTos(dodoPizza_1, dodoPizza_2)));
    }

    @Test
    void update() throws Exception {
        DishTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + DODO_PIZZA_ID_1).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_TO_MATCHER.assertMatch(convertToDto(dishService.get(DODO_PIZZA_ID_1, DODO_PIZZA_ID)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        DishTo newDish = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        DishTo created = DISH_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_TO_MATCHER.assertMatch(created, newDish);
        DISH_TO_MATCHER.assertMatch(convertToDto(dishService.get(newId, DODO_PIZZA_ID)), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        DishTo invalid = new DishTo(null, null, 200, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        DishTo invalid = new DishTo(YAKITORIYA_ID_1, null, 0, LocalDate.now());
        perform(MockMvcRequestBuilders.put(REST_URL + YAKITORIYA_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        DishTo invalid = convertToDto(getUpdated());
        invalid.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + DODO_PIZZA_ID_1)
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
        DishTo invalid = convertToDto(getUpdated());
        invalid.setName(dodoPizza_2.getName());
        invalid.setRegistered(of(2021, Month.NOVEMBER, 11));

        perform(MockMvcRequestBuilders.put(REST_URL + DODO_PIZZA_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME_AND_DATE));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        DishTo invalid = convertToDto(getNew());
        invalid.setName(dodoPizza_2.getName());
        invalid.setRegistered(of(2021, Month.NOVEMBER, 11));

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME_AND_DATE));
    }
}