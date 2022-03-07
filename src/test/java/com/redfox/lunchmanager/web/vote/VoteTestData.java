package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.MatcherFactory;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.to.VoteTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.yakitoriya;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.mcdonalds;
import static com.redfox.lunchmanager.web.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user", "restaurantId", "userId");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE_ID_1 = START_SEQ + 16;
    public static final int VOTE_ID_2 = START_SEQ + 17;
    public static final int VOTE_ID_3 = START_SEQ + 18;
    public static final int VOTE_ID_4 = START_SEQ + 19;
    public static final int NOT_FOUND = START_SEQ;

    public static final Vote vote1 = new Vote(VOTE_ID_1, admin1, yakitoriya, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID_2, admin1, yakitoriya, LocalDate.of(2021, Month.NOVEMBER, 11));
    public static final Vote vote3 = new Vote(VOTE_ID_3, admin2, yakitoriya, LocalDate.now());
    public static final Vote vote4 = new Vote(VOTE_ID_4, user3, mcdonalds, LocalDate.now());

    public static final List<Vote> votes = List.of(vote1, vote2);

    public static Vote getNew() {
        Vote vote = new Vote(user3, mcdonalds, LocalDate.now());
        vote.setUserId(user3.id());
        vote.setRestaurantId(mcdonalds.id());
        return vote;
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE_ID_3, admin2, mcdonalds, LocalDate.now());
        vote.setUserId(admin2.id());
        vote.setRestaurantId(mcdonalds.id());
        return vote;
    }
}
