package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {
    @Autowired
    protected UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", user2.getEmail(), "newPass",
                        of(2021, Month.NOVEMBER, 2, 21, 45), Role.USER)));
    }

    @Test
    void delete() {
        service.delete(USER_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_1));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = service.get(USER_ID_1);
        MATCHER.assertMatch(user, user1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail(user3.getEmail());
        MATCHER.assertMatch(user, user3);
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("mail"));
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        MATCHER.assertMatch(all, users);
    }

    @Test
    void update() {
        User user = getUpdated();
        service.update(user);
        MATCHER.assertMatch(service.get(USER_ID_3), getUpdated());
    }

    @Test
    void updateNotFound() {
        User user = getUpdated();
        user.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(user));
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new User("  ", "mail@yandex.ru", "password", LocalDateTime.now(), Role.USER)));
        validateRootCause(ConstraintViolationException.class,
                () -> service.create(new User("User", "  ", "password", LocalDateTime.now(), Role.USER)));
    }

    @Test
    void enable() {
        service.enable(USER_ID_1, false);
        assertFalse(service.get(USER_ID_1).isEnabled());
        service.enable(USER_ID_1, true);
        assertTrue(service.get(USER_ID_1).isEnabled());
    }
}