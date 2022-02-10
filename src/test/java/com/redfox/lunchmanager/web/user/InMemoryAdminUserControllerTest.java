package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.util.exception.IllegalRequestDataException;
import com.redfox.lunchmanager.util.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static com.redfox.lunchmanager.web.user.UserTestData.*;
import static com.redfox.lunchmanager.util.Users.convertToDto;
import static com.redfox.lunchmanager.util.Users.getTos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class InMemoryAdminUserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminUserControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminUserController controller;
    private static InMemoryUserRepository repository;

    @BeforeAll
    static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminUserController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterAll
    static void afterClass() {
//        May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
//        http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @BeforeEach
    void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    public void create() {
        UserTo expected = convertToDto(getNew());
        int id = controller.create(expected).getId();
        assertEquals(expected, controller.get(id));
    }

    @Test
    public void createNotNew() {
        assertThrows(IllegalRequestDataException.class, () -> controller.create(convertToDto(getUpdated())));
    }

    @Test
    public void delete() {
        controller.delete(USER_ID_1);
        Assertions.assertNull(repository.get(USER_ID_1));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        assertEquals(convertToDto(user1), controller.get(USER_ID_1));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    public void getByMail() {
        assertEquals(convertToDto(user3), controller.getByMail(user3.getEmail()));
    }

    @Test
    public void getByMailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("mail"));
    }

    @Test
    public void getAll() {
        assertEquals(getTos(users), controller.getAll());
    }

    @Test
    public void update() {
        UserTo user = convertToDto(getUpdated());
        controller.update(user, USER_ID_3);
        assertEquals(user, controller.get(USER_ID_3));
    }

    @Test
    public void updateAssureIdConsistent() {
        assertThrows(IllegalRequestDataException.class, () -> controller.update(convertToDto(getUpdated()), NOT_FOUND));
    }
}