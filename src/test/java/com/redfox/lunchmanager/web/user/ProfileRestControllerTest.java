package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
import static com.redfox.lunchmanager.web.user.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(user3)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        TO_MATCHER.assertMatch(getTos(userService.getAll()), getTos(user1, user2));
    }

    @Test
    void update() throws Exception {
        UserTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(userService.get(USER_ID_3)), updated);
    }
}