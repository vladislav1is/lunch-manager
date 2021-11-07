package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.ActiveDbProfileResolver;
import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.redfox.lunchmanager.UserTestData.*;
import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class UserServiceTest {

    private static final Logger log = getLogger("result");
    private static final StringBuilder results = new StringBuilder();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @Autowired
    private UserService service;

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
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