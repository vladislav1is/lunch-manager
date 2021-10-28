package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static com.redfox.lunchmanager.UserTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@Ignore
public class InMemoryAdminRestControllerTest {

    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;
    private static InMemoryUserRepository repository;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminRestController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    public void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    public void create() {
        User expected = getNew();
        int id = controller.create(expected).getId();
        assertEquals(expected, controller.get(id));
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
        assertEquals(user1, controller.get(USER_ID_1));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    public void getByMail() {
        assertEquals(user3, controller.getByMail(user3.getEmail()));
    }

    @Test
    public void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("mail"));
    }

    @Test
    public void getAll() {
        assertEquals(users, controller.getAll());
    }

    @Test
    public void update() {
        User user = getUpdated();
        controller.update(user, USER_ID_3);
        assertEquals(user, controller.get(USER_ID_3));
    }

    @Test
    public void updateAssureIdConsistent() {
        assertThrows(IllegalArgumentException.class, () -> controller.update(getUpdated(), NOT_FOUND));
    }
}