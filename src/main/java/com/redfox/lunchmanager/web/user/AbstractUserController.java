package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.to.UserTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

import static com.redfox.lunchmanager.util.Users.*;
import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {

    private static final Logger log = getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public UserTo create(UserTo userTo) {
        User user = convertToEntity(userTo);
        log.info("create {}", user);
        checkNew(user);
        service.create(user);
        userTo.setId(user.id());
        return userTo;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public UserTo get(int id) {
        log.info("get {}", id);
        return convertToDto(service.get(id));
    }

    public UserTo getByMail(String email) {
        log.info("getByEmail {}", email);
        return convertToDto(service.getByEmail(email));
    }

    public List<UserTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll());
    }

    public void update(UserTo userTo, int id) {
        User user = convertToEntity(userTo);
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }
}
