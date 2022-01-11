package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;

import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
import static com.redfox.lunchmanager.util.exception.ErrorType.VALIDATION_ERROR;
import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Autowired
    private UserService userService;

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
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID_1)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                //  https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(user1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + user1.getEmail())
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(user1)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID_1)
                .with(userHttpBasic(user2)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID_1));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        UserTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID_3)
                .with(userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, updated.getPassword())))
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(userService.get(USER_ID_3)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        UserTo newUser = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(status().isCreated());

        UserTo created = TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newUser.setId(newId);
        TO_MATCHER.assertMatch(created, newUser);
        TO_MATCHER.assertMatch(convertToDto(userService.get(newId)), newUser);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(getTos(user1, user2, user3)));
    }

    @Test
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + USER_ID_1)
                .with(userHttpBasic(user1))
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(userService.get(USER_ID_1).isEnabled());
    }

    @Test
    void createInvalid() throws Exception {
        UserTo invalid = new UserTo(null, null, "", "newPass", Boolean.TRUE, LocalDateTime.now().truncatedTo(ChronoUnit.HOURS), EnumSet.of(Role.USER, Role.ADMIN));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(invalid, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo invalid = convertToDto(user3);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID_3)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(invalid, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        UserTo updated = convertToDto(user3);
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID_3)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(updated, "password1")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserTo updated = convertToDto(user3);
        updated.setEmail("admin@gmail.com");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID_3)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        UserTo expected = convertToDto(getNew());
        expected.setEmail("admin@gmail.com");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(expected, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL));
    }
}