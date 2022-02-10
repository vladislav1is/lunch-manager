package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.web.user.UserTestData;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import com.redfox.lunchmanager.web.restaurant.ProfileRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.web.user.UserTestData.user1;
import static com.redfox.lunchmanager.web.user.UserTestData.user2;
import static com.redfox.lunchmanager.web.vote.VoteTestData.*;
import static com.redfox.lunchmanager.util.Votes.convertToDto;
import static com.redfox.lunchmanager.util.Votes.getTos;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestaurantController.REST_URL + '/' + RESTAURANT_ID_1 + "/votes/";

    @Autowired
    private VoteService service;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID_1)
                .with(userHttpBasic(user1)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(VOTE_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID_1)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(vote1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?localDate=" + vote1.getRegistered())
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(convertToDto(vote1)));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(getTos(votes)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .with(userHttpBasic(user1))
                .param("startDate", "2021-11-11")
                .param("endDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TO_MATCHER.contentJson(getTos(vote2)));
    }

    @Test
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endDate=")
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(TO_MATCHER.contentJson(getTos(votes)));
    }

    @Test
    void update() throws Exception {
        VoteTo updated = convertToDto(VoteTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID_3).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        TO_MATCHER.assertMatch(convertToDto(service.get(VOTE_ID_3, RESTAURANT_ID_1)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        VoteTo newVote = convertToDto(VoteTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user1))
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