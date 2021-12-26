package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.to.VoteTo;
import com.redfox.lunchmanager.util.Restaurants;
import com.redfox.lunchmanager.web.AbstractControllerTest;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.TestUtil.userAuth;
import static com.redfox.lunchmanager.UserTestData.user3;
import static com.redfox.lunchmanager.VoteTestData.*;
import static com.redfox.lunchmanager.util.Votes.getTos;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String URL = "/restaurants/" + RESTAURANT_ID_1 + "/votes";

    @Test
    void unAuth() throws Exception {
        perform(get(URL))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getVotesForToday() throws Exception {
        perform(get(URL)
                .with(userAuth(user3)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("vote-form"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/vote-form.jsp"))
                .andExpect(model().attribute("restaurant", Restaurants.convertToDto(restaurant1)))
                .andExpect(model().attribute("votes",
                        new AssertionMatcher<List<VoteTo>>() {
                            @Override
                            public void assertion(List<VoteTo> actual) throws AssertionError {
                                TO_MATCHER.assertMatch(actual, getTos(vote1, vote3));
                            }
                        }
                ));
    }
}