package com.redfox.lunchmanager.web;

import org.junit.Test;

import static com.redfox.lunchmanager.UserTestData.USER_ID_1;
import static com.redfox.lunchmanager.UserTestData.user1;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(3)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(USER_ID_1)),
                                hasProperty("name", is(user1.getName()))
                        )
                )));
    }
}