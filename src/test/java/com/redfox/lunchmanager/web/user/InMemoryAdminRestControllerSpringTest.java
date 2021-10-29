package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
@Ignore
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;
    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    public void create() {
        User created = controller.create(getNew());
        Integer newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    public void createNotNew() {
        assertThrows(IllegalArgumentException.class, () -> controller.create(getUpdated()));
    }

    @Test
    public void delete() {
        controller.delete(USER_ID_1);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID_1));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = controller.get(USER_ID_1);
        MATCHER.assertMatch(user, user1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    public void getByMail() {
        User user = controller.getByMail(user3.getEmail());
        MATCHER.assertMatch(user, user3);
    }

    @Test
    public void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("mail"));
    }

    @Test
    public void getAll() {
        List<User> all = controller.getAll();
        MATCHER.assertMatch(all, users);
    }

    @Test
    public void update() {
        User user = getUpdated();
        controller.update(user, USER_ID_3);
        MATCHER.assertMatch(controller.get(USER_ID_3), getUpdated());
    }

    @Test
    public void updateAssureIdConsistent() {
        assertThrows(IllegalArgumentException.class, () -> controller.update(getUpdated(), NOT_FOUND));
    }
}
