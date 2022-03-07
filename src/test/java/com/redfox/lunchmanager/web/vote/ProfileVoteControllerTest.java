package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.MatcherFactory;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.util.Votes;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import com.redfox.lunchmanager.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.redfox.lunchmanager.TestUtil.userHttpBasic;
import static com.redfox.lunchmanager.util.Votes.convertToDto;
import static com.redfox.lunchmanager.util.Votes.getTos;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.web.vote.ProfileVoteController.REST_URL;
import static com.redfox.lunchmanager.web.vote.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileVoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService service;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void voteToday() throws Exception {
        Votes.setDeadline(LocalTime.MAX);
        VoteTo newVote = convertToDto(VoteTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user3))
                .param("restaurantId", Integer.toString(MCDONALDS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated())
                .andDo(print());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(convertToDto(service.getBy(LocalDate.now(), USER_ID_3)), newVote);
    }

    @Test
    void revoteTodayBeforeDeadline() throws Exception {
        Votes.setDeadline(LocalTime.MAX);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(admin1))
                .param("restaurantId", Integer.toString(DODO_PIZZA_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(service.getBy(LocalDate.now(), ADMIN_ID_1).getRestaurantId(), DODO_PIZZA_ID);
    }

    @Test
    void revoteTodayAfterDeadline() throws Exception {
        Votes.setDeadline(LocalTime.MIN);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(admin1))
                .param("restaurantId", Integer.toString(DODO_PIZZA_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // TODO: Fix error type
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteTodayBeforeDeadline() throws Exception {
        Votes.setDeadline(LocalTime.MAX);
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isNoContent());
        assertNull(service.getBy(LocalDate.now(), ADMIN_ID_1));
    }

    @Test
    void deleteTodayAfterDeadline() throws Exception {
        Votes.setDeadline(LocalTime.MIN);
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(admin1)))
                .andDo(print())
                // TODO: Fix error type
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getOwnByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date")
                .with(userHttpBasic(admin1))
                .param("voteDate", "2021-11-11"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(convertToDto(vote2)));
    }

    @Test
    void getAllOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getTos(votes)));
    }

    @Test
    void countByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/count")
                .with(userHttpBasic(admin1))
                .param("voteDate", String.valueOf(LocalDate.now()))
                .param("restaurantId", Integer.toString(YAKITORIYA_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MatcherFactory.usingEqualsComparator(Integer.class).contentJson(2));
    }
}