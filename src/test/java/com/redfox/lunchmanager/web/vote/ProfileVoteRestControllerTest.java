package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.UserTestData;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.SecurityUtil;
import com.redfox.lunchmanager.web.json.JsonUtil;
import com.redfox.lunchmanager.web.restaurant.ProfileRestRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.VoteTestData.*;
import static com.redfox.lunchmanager.util.Votes.convertToDto;
import static com.redfox.lunchmanager.util.Votes.getTos;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestRestaurantController.REST_URL + '/' + RESTAURANT_ID_1 + "/votes/";

    @Autowired
    private VoteService service;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID_1))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(VOTE_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(vote1)));
    }

    @Test
    void getByDate() throws Exception {
        SecurityUtil.setAuthUserId(UserTestData.USER_ID_1);
        perform(MockMvcRequestBuilders.get(REST_URL + "by?localDate=" + vote1.getRegistered()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(vote1)));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(getTos(votes)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2021-11-11")
                .param("endDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TO_MATCHER.contentJson(getTos(vote2)));
    }

    @Test
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endDate="))
                .andExpect(status().isOk())
                .andExpect(TO_MATCHER.contentJson(getTos(votes)));
    }

    @Test
    void update() throws Exception {
        SecurityUtil.setAuthUserId(UserTestData.USER_ID_2);
        VoteTo updated = convertToDto(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID_3).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(service.get(VOTE_ID_3, RESTAURANT_ID_1)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        VoteTo newVote = convertToDto(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        VoteTo created = TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);
        TO_MATCHER.assertMatch(created, newVote);
        TO_MATCHER.assertMatch(convertToDto(service.get(newId, RESTAURANT_ID_1)), newVote);
    }
}