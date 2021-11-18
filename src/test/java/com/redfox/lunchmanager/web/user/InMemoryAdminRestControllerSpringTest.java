package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(locations = {"classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;
    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    void create() {
        User created = controller.create(getNew());
        Integer newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    void createNotNew() {
        assertThrows(IllegalArgumentException.class, () -> controller.create(getUpdated()));
    }

    @Test
    void delete() {
        controller.delete(USER_ID_1);
        assertNull(repository.get(USER_ID_1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = controller.get(USER_ID_1);
        MATCHER.assertMatch(user, user1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    void getByMail() {
        User user = controller.getByMail(user3.getEmail());
        MATCHER.assertMatch(user, user3);
    }

    @Test
    void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("mail"));
    }

    @Test
    void getAll() {
        List<User> all = controller.getAll();
        MATCHER.assertMatch(all, users);
    }

    @Test
    void update() {
        User user = getUpdated();
        controller.update(user, USER_ID_3);
        MATCHER.assertMatch(controller.get(USER_ID_3), getUpdated());
    }

    @Test
    void updateAssureIdConsistent() {
        assertThrows(IllegalArgumentException.class, () -> controller.update(getUpdated(), NOT_FOUND));
    }
}
