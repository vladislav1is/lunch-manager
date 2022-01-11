package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.exception.ErrorType;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static com.redfox.lunchmanager.Profiles.HEROKU;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.exception.UpdateRestrictionException.EXCEPTION_UPDATE_RESTRICTION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(HEROKU)
class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    //  Set DATABASE_URL environment for heroku profile
    static {
        Resource resource = new ClassPathResource("db/postgres.properties");
        try {
            ResourcePropertySource propertySource = new ResourcePropertySource(resource);
            String herokuDbUrl = String.format("postgres://%s:%s@%s",
                    propertySource.getProperty("database.username"),
                    propertySource.getProperty("database.password"),
                    ((String) propertySource.getProperty("database.url")).substring(18));
            System.out.println(herokuDbUrl);

            System.setProperty("DATABASE_URL", herokuDbUrl);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

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
