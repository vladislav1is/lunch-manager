package com.redfox.lunchmanager;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.to.VoteTo;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");
    public static final MatcherFactory.Matcher<VoteTo> TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE_ID_1 = START_SEQ + 16;
    public static final int VOTE_ID_2 = START_SEQ + 17;
    public static final int VOTE_ID_3 = START_SEQ + 18;
    public static final int VOTE_ID_4 = START_SEQ + 19;
    public static final int NOT_FOUND = START_SEQ;

    public static final Vote vote1 = new Vote(VOTE_ID_1, user1, restaurant1, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID_2, user1, restaurant1, LocalDate.of(2021, Month.NOVEMBER, 11));
    public static final Vote vote3 = new Vote(VOTE_ID_3, user2, restaurant1, LocalDate.now());
    public static final Vote vote4 = new Vote(VOTE_ID_4, user3, restaurant3, LocalDate.now());

    public static final List<Vote> votes = List.of(vote1, vote3, vote2);

    public static Vote getNew() {
        return new Vote(user3, restaurant1, LocalDate.of(2020, Month.DECEMBER, 2));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID_3, user2, restaurant1, vote3.getRegistered().plus(2, ChronoUnit.DAYS));
    }
}
