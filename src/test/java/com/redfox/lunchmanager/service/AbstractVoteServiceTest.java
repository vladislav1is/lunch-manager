package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.util.Votes;
import com.redfox.lunchmanager.util.exception.DataConflictException;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import com.redfox.lunchmanager.web.restaurant.RestaurantTestData;
import com.redfox.lunchmanager.web.vote.VoteTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static com.redfox.lunchmanager.TestUtil.mockAuthorize;
import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.*;
import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.web.vote.VoteTestData.NOT_FOUND;
import static com.redfox.lunchmanager.web.vote.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService service;

    @Autowired
    private VoteRepository repository;

    @BeforeEach
    void init() {
        mockAuthorize(admin1);
    }

    @Test
    void createToday() {
        Vote created = service.createToday(MCDONALDS_ID, USER_ID_3);
        int newId = created.id();
        Vote newVote = VoteTestData.getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.getBy(LocalDate.now(), USER_ID_3), newVote);
    }

    @Test
    void duplicateCreate() {
        assertThrows(DataConflictException.class, () -> service.createToday(MCDONALDS_ID, ADMIN_ID_1));
    }

    @Test
    void updateTodayBeforeDeadline() {
        Votes.setDeadline(LocalTime.MAX);
        Vote updated = VoteTestData.getUpdated();
        service.updateToday(updated.getUserId(), updated.getRestaurantId(), false);
        VOTE_MATCHER.assertMatch(service.getBy(LocalDate.now(), ADMIN_ID_2), updated);
    }

    @Test
    void updateTodayAfterDeadline() {
        Votes.setDeadline(LocalTime.MIN);
        Vote updated = VoteTestData.getUpdated();
        assertThrows(DataConflictException.class, () -> service.updateToday(updated.getUserId(), updated.getRestaurantId(), false));
    }

    @Test
    void deleteTodayBeforeDeadline() {
        Votes.setDeadline(LocalTime.MAX);
        service.updateToday(ADMIN_ID_1, YAKITORIYA_ID, true);
        assertNull(service.getBy(LocalDate.now(), ADMIN_ID_1));
    }

    @Test
    void deleteTodayAfterDeadline() {
        Votes.setDeadline(LocalTime.MIN);
        assertThrows(DataConflictException.class, () -> service.updateToday(ADMIN_ID_1, YAKITORIYA_ID, true));
    }

    @Test
    void deleteAll() {
        service.deleteAll(YAKITORIYA_ID);
        assertThrows(NotFoundException.class, () -> service.deleteAll(NOT_FOUND));
    }

    @Test
    void deleteAllNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteAll(RestaurantTestData.NOT_FOUND));
    }

    @Test
    void getByDate() {
        Vote actual = service.getBy(LocalDate.of(2021, Month.NOVEMBER, 11), ADMIN_ID_1);
        System.out.println(actual);
        VOTE_MATCHER.assertMatch(actual, vote2);
    }

    @Test
    void getAll() {
        VOTE_MATCHER.assertMatch(service.getAll(ADMIN_ID_1), votes);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> repository.save(new Vote(admin1, yakitoriya, null), YAKITORIYA_ID));
    }

    @Test
    void countByDate() {
        int actual = service.countBy(LocalDate.now(), YAKITORIYA_ID);
        assertEquals(2, actual);
    }
}