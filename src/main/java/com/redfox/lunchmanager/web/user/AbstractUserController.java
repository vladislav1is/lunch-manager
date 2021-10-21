package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {

    private static final Logger log = getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }
}
