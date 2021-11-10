package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import java.time.Month;
import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertThrows;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;
    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", user2.getEmail(), "newPass",
                        of(2021, Month.NOVEMBER, 2, 21, 45), Role.USER)));
    }

    @Test
    public void delete() {
        service.delete(USER_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_1));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(USER_ID_1);
        MATCHER.assertMatch(user, user1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(user3.getEmail());
        MATCHER.assertMatch(user, user3);
    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("mail"));
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        MATCHER.assertMatch(all, users);
    }

    @Test
    public void update() {
        User user = getUpdated();
        service.update(user);
        MATCHER.assertMatch(service.get(USER_ID_3), getUpdated());
    }

    @Test
    public void updateNotFound() {
        User user = getUpdated();
        user.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(user));
    }
}