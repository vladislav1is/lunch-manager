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

import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(locations = {"classpath:spring/inmemory.xml"})
class InMemoryAdminUserControllerSpringTest {

    @Autowired
    private AdminUserController controller;
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
        USER_TO_MATCHER.assertMatch(created, newUser);
        USER_TO_MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    void createNotNew() {
        assertThrows(IllegalRequestDataException.class, () -> controller.create(convertToDto(getUpdated())));
    }

    @Test
    void delete() {
        controller.delete(ADMIN_ID_1);
        assertNull(repository.get(ADMIN_ID_1));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    void get() {
        UserTo user = controller.get(ADMIN_ID_1);
        USER_TO_MATCHER.assertMatch(user, convertToDto(admin1));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    void getByMail() {
        UserTo user = controller.getBy(user3.getEmail());
        USER_TO_MATCHER.assertMatch(user, convertToDto(user3));
    }

    @Test
    void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getBy("mail"));
    }

    @Test
    void getAll() {
        List<UserTo> all = controller.getAll();
        USER_TO_MATCHER.assertMatch(all, getTos(users));
    }

    @Test
    void update() {
        UserTo user = convertToDto(getUpdated());
        controller.update(user, USER_ID_3);
        USER_TO_MATCHER.assertMatch(controller.get(USER_ID_3), convertToDto(getUpdated()));
    }

    @Test
    void updateAssureIdConsistent() {
        assertThrows(IllegalRequestDataException.class, () -> controller.update(convertToDto(getUpdated()), NOT_FOUND));
    }
}
