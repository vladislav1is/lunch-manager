package com.redfox.lunchmanager.service;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFound;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        checkNotFoundWithId(repository.save(user), user.getId());
    }
}
