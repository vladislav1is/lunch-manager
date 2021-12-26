package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.VoteTestData;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static com.redfox.lunchmanager.RestaurantTestData.*;
import static com.redfox.lunchmanager.TestUtil.mockAuthorize;
import static com.redfox.lunchmanager.UserTestData.USER_ID_1;
import static com.redfox.lunchmanager.UserTestData.user1;
import static com.redfox.lunchmanager.VoteTestData.MATCHER;
import static com.redfox.lunchmanager.VoteTestData.NOT_FOUND;
import static com.redfox.lunchmanager.VoteTestData.getNew;
import static com.redfox.lunchmanager.VoteTestData.getUpdated;
import static com.redfox.lunchmanager.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService service;

    @BeforeEach
    void init() {
        mockAuthorize(user1);
    }

    @Test
    void create() {
        Vote created = service.create(getNew(), RESTAURANT_ID_1);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.get(newId, RESTAURANT_ID_1), newVote);
    }

    @Test
    void duplicateCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Vote(user1, restaurant1, LocalDate.now()), RESTAURANT_ID_1));
    }

    @Test
    void delete() {
        service.delete(VOTE_ID_1, RESTAURANT_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(VOTE_ID_1, RESTAURANT_ID_1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_ID_1));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(VOTE_ID_3, RESTAURANT_ID_2));
    }

    @Test
    void get() {
        Vote actual = service.get(VOTE_ID_2, RESTAURANT_ID_1);
        MATCHER.assertMatch(actual, vote2);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_ID_1));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(VOTE_ID_4, RESTAURANT_ID_1));
    }

    @Test
    void getByDate() {
        Vote actual = service.getByDate(LocalDate.of(2021, Month.NOVEMBER, 11), USER_ID_1);
        VoteTestData.MATCHER.assertMatch(actual, vote2);
    }

    @Test
    void getAll() {
        MATCHER.assertMatch(service.getAll(RESTAURANT_ID_1), votes);
    }

    @Test
    void getBetweenHalfOpen() {
        MATCHER.assertMatch(service.getBetweenHalfOpen(
                LocalDate.of(2021, Month.NOVEMBER, 11),
                LocalDate.of(2021, Month.NOVEMBER, 11), RESTAURANT_ID_1), vote2);
    }

    @Test
    void getBetweenWithNullDates() {
        MATCHER.assertMatch(service.getBetweenHalfOpen(null, null, RESTAURANT_ID_1), votes);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.update(updated, RESTAURANT_ID_2);
        MATCHER.assertMatch(service.get(VOTE_ID_3, RESTAURANT_ID_2), getUpdated());
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Vote(user1, restaurant1, null), RESTAURANT_ID_1));
    }
}