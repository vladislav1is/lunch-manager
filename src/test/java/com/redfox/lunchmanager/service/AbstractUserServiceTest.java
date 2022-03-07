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

import static com.redfox.lunchmanager.web.user.UserTestData.*;
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
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", admin2.getEmail(), "newPass",
                        of(2021, Month.NOVEMBER, 2, 21, 45), Role.USER)));
    }

    @Test
    void delete() {
        service.delete(ADMIN_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_ID_1));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID_1);
        USER_MATCHER.assertMatch(user, admin1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getBy(user3.getEmail());
        USER_MATCHER.assertMatch(user, user3);
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getBy("mail"));
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, users);
    }

    @Test
    void update() {
        User user = getUpdated();
        service.update(user);
        USER_MATCHER.assertMatch(service.get(USER_ID_3), getUpdated());
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
        service.enable(ADMIN_ID_1, false);
        assertFalse(service.get(ADMIN_ID_1).isEnabled());
        service.enable(ADMIN_ID_1, true);
        assertTrue(service.get(ADMIN_ID_1).isEnabled());
    }
}