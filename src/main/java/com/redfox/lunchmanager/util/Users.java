package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.User;

import static com.redfox.lunchmanager.model.Role.ADMIN;
import static com.redfox.lunchmanager.model.Role.USER;

public class Users {

    public static final User user1 = new User("Alex", "@yandex.ru", "1234", USER, ADMIN);
    public static final User user2 = new User("Bob", "@mail.ru", "1234", USER);
    public static final User user3 = new User("Elvis", "@gmail.com", "1234", USER);
}
