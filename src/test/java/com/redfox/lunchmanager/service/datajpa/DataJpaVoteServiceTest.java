package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.web.vote.VoteTestData;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.AbstractVoteServiceTest;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.DATAJPA;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.web.vote.VoteTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaVoteServiceTest extends AbstractVoteServiceTest {
    @Test
    void getWithRestaurant() {
        Vote vote = service.getWithRestaurant(VoteTestData.VOTE_ID_1, RESTAURANT_ID_1);
        MATCHER.assertMatch(vote, vote1);
        RestaurantTestData.MATCHER.assertMatch(vote.getRestaurant(), restaurant1);
    }

    @Test
    void getWithRestaurantNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithRestaurant(NOT_FOUND, RESTAURANT_ID_1));
    }
}