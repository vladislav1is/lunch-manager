package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.util.Restaurants;
import com.redfox.lunchmanager.util.Votes;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.redfox.lunchmanager.TestUtil.userAuth;
import static com.redfox.lunchmanager.util.Votes.canVoteAfter;
import static com.redfox.lunchmanager.util.Votes.deadline;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.web.user.UserTestData.admin1;
import static com.redfox.lunchmanager.web.vote.VoteTestData.vote1;
import static com.redfox.lunchmanager.web.vote.VoteTestData.votes;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    void unAuth() throws Exception {
        perform(get("/restaurants/" + YAKITORIYA_ID + "/vote"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getRestaurant() throws Exception {
        Votes.setDeadline(LocalTime.MAX);
        final RestaurantTo restaurant = Restaurants.convertToDto(yakitoriya, vote1, votes.size());
        perform(get("/restaurants/" + YAKITORIYA_ID + "/vote")
                .with(userAuth(admin1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("vote"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/vote.jsp"))
                .andExpect(model().attribute("restaurant", new AssertionMatcher<RestaurantTo>() {
                            @Override
                            public void assertion(RestaurantTo actual) throws AssertionError {
                                RESTAURANT_TO_MATCHER.assertMatch(actual, restaurant);
                            }
                        }
                ))
                .andExpect(model().attribute("enabled", new AssertionMatcher<Boolean>() {
                            @Override
                            public void assertion(Boolean actual) throws AssertionError {
                                assertTrue(!canVoteAfter(deadline) || restaurant.getVoteTo() == null);
                            }
                        }
                ));
    }
}