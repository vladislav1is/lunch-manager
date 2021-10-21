package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.service.UserService;

import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    private UserService service;

    public User create(User user) {
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public User get(int id) {
        return service.get(id);
    }

    public User getByMail(String email) {
        return service.getByEmail(email);
    }

    public List<User> getAll() {
        return service.getAll();
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        service.update(user);
    }
}
