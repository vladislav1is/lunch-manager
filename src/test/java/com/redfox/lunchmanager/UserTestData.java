package com.redfox.lunchmanager;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;

import java.util.List;

import static com.redfox.lunchmanager.model.Role.ADMIN;
import static com.redfox.lunchmanager.model.Role.USER;
import static com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {

    public static final int USER_ID_1 = START_SEQ + 1;
    public static final int USER_ID_2 = START_SEQ + 2;
    public static final int USER_ID_3 = START_SEQ + 3;
    public static final int NOT_FOUND = START_SEQ;

    public static final User user1 = new User(USER_ID_1, "Alex", "@yandex.ru", "1234", USER, ADMIN);
    public static final User user2 = new User(USER_ID_2, "Bob", "@mail.ru", "1234", USER, ADMIN);
    public static final User user3 = new User(USER_ID_3, "Elvis", "@gmail.com", "1234", USER);

    public static final List<User> users = List.of(user1, user2, user3);

    public static User getNew() {
        return new User(null, "Pete", "@mail1.ru", "123", Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_ID_3, "Pete", "@mail2.ru", "123", Role.USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
