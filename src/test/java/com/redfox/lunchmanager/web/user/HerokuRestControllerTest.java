package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.exception.ErrorType;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.Profiles.HEROKU;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.exception.UpdateRestrictionException.EXCEPTION_UPDATE_RESTRICTION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(HEROKU)
class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID_3)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        UserTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID_3)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(jsonWithPassword(updated, updated.getPassword())))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }
}
