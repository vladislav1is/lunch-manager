package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Users {

    public Users() {
    }

    public static UserTo convertToDto(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.isEnabled(), user.getRegistered(), user.getRoles());
    }

    public static User convertToEntity(UserTo userTo) {
        return new User(userTo.getId(), userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(),
                userTo.getEnabled(), userTo.getRegistered(), userTo.getRoles());
    }

    public static List<UserTo> getTos(Collection<User> users) {
        return users.stream()
                .map(Users::convertToDto)
                .toList();
    }

    public static List<UserTo> getTos(User... users) {
        return Arrays.stream(users)
                .map(Users::convertToDto)
                .toList();
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
