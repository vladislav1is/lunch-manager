package com.redfox.lunchmanager.repository;

import com.redfox.lunchmanager.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getBy(String email);

    // ORDERED name email
    List<User> getAll();
}