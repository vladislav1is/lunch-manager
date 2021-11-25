package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.to.UserTo;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.getTos;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<UserTo>>() {
                            @Override
                            public void assertion(List<UserTo> actual) throws AssertionError {
                                TO_MATCHER.assertMatch(actual, getTos(user1, user2, user3));
                            }
                        }
                ));
    }
}