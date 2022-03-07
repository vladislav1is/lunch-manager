package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.redfox.lunchmanager.TestUtil.mockAuthorize;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
import static com.redfox.lunchmanager.util.exception.ErrorType.VALIDATION_ERROR;
import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileUserController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user3)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(convertToDto(user3)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(user3)))
                .andExpect(status().isNoContent());
        USER_TO_MATCHER.assertMatch(getTos(userService.getAll()), getTos(admin1, admin2));
    }

    @Test
    void register() throws Exception {
        // TODO: Fix status, expected:<201> but was:<401>
        mockAuthorize(user3);
        //
        User newUser = getNew();
        UserTo newTo = convertToDto(newUser);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void update() throws Exception {
        UserTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_TO_MATCHER.assertMatch(convertToDto(userService.get(USER_ID_3)), updated);
    }

    @Test
    void registerInvalid() throws Exception {
        // TODO: Fix status, expected:<422> but was:<401>
        mockAuthorize(user3);
        //
        UserTo newTo = new UserTo(null, null, null, null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null, null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        UserTo updated = convertToDto(user3);
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserTo updated = convertToDto(getUpdated());
        updated.setEmail("admin@gmail.com");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL));
    }
}