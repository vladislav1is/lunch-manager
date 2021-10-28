package com.redfox.lunchmanager.repository.service;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.redfox.lunchmanager.UserTestData.*;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcUserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void create() {
        User created = service.create(getNew());
        Integer newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        assertMatch(created, newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "@mail.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() {
        service.delete(USER_ID_1);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_1));
    }


    @Test
    public void get() {
        User user = service.get(USER_ID_1);
        assertMatch(user, user1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(user3.getEmail());
        assertMatch(user, user3);
    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("mail"));
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        assertMatch(all, users);
    }

    @Test
    public void update() {
        User user = getUpdated();
        service.update(user);
        assertMatch(service.get(USER_ID_3), user);
    }

    @Test
    public void updateNotFound() {
        User user = getUpdated();
        user.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(user));
    }
}