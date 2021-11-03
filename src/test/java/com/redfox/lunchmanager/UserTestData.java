package com.redfox.lunchmanager;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;

import java.time.Month;
import java.util.Collections;
import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static com.redfox.lunchmanager.model.Role.ADMIN;
import static com.redfox.lunchmanager.model.Role.USER;
import static java.time.LocalDateTime.of;

public class UserTestData {
    public static final MatcherFactory<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("registered", "roles");

    public static final int USER_ID_1 = START_SEQ + 1;
    public static final int USER_ID_2 = START_SEQ + 2;
    public static final int USER_ID_3 = START_SEQ + 3;
    public static final int NOT_FOUND = START_SEQ;

    public static final User user1 = new User(USER_ID_1, "Alex", "user1@yandex.ru", "123456", of(2021, Month.NOVEMBER, 2, 21, 45), USER, ADMIN);
    public static final User user2 = new User(USER_ID_2, "Bob", "user2@mail.ru", "123456", of(2021, Month.NOVEMBER, 2, 21, 45), USER, ADMIN);
    public static final User user3 = new User(USER_ID_3, "Elvis", "user3@gmail.com", "123456", of(2021, Month.NOVEMBER, 2, 21, 45), USER);

    public static final List<User> users = List.of(user1, user2, user3);

    public static User getNew() {
        return new User(null, "Pete", "user4@mail.ru", "123456", of(2021, Month.NOVEMBER, 2, 21, 45), Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user3);
        updated.setName("UpdatedName");
        updated.setEmail("user3@mail.ru");
        updated.setPassword("newPass");
        updated.setRegistered(of(2021, Month.NOVEMBER, 2, 21, 45));
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(ADMIN));
        return updated;
    }
}
