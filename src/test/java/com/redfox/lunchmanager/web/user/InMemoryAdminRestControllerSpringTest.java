package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.exception.IllegalRequestDataException;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
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
        UserTo created = controller.create(convertToDto(getNew()));
        Integer newId = created.getId();
        UserTo newUser = convertToDto(getNew());
        newUser.setId(newId);
        TO_MATCHER.assertMatch(created, newUser);
        TO_MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    void createNotNew() {
        assertThrows(IllegalRequestDataException.class, () -> controller.create(convertToDto(getUpdated())));
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
        UserTo user = controller.get(USER_ID_1);
        TO_MATCHER.assertMatch(user, convertToDto(user1));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    void getByMail() {
        UserTo user = controller.getByMail(user3.getEmail());
        TO_MATCHER.assertMatch(user, convertToDto(user3));
    }

    @Test
    void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("mail"));
    }

    @Test
    void getAll() {
        List<UserTo> all = controller.getAll();
        TO_MATCHER.assertMatch(all, getTos(users));
    }

    @Test
    void update() {
        UserTo user = convertToDto(getUpdated());
        controller.update(user, USER_ID_3);
        TO_MATCHER.assertMatch(controller.get(USER_ID_3), convertToDto(getUpdated()));
    }

    @Test
    void updateAssureIdConsistent() {
        assertThrows(IllegalRequestDataException.class, () -> controller.update(convertToDto(getUpdated()), NOT_FOUND));
    }
}
