package com.redfox.lunchmanager.web.converter;

import com.redfox.lunchmanager.model.Role;
import org.springframework.format.Formatter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class UserFormatters {
    public static class RoleFormatter implements Formatter<Role> {

        @Override
        public Role parse(String text, Locale locale) {
            return Role.valueOf(text);
        }

        @Override
        public String print(Role role, Locale locale) {
            return role.toString();
        }
    }

    public static class RolesFormatter implements Formatter<Set<Role>> {
        @Override
        public Set<Role> parse(String text, Locale locale) {
            String[] roles = text.substring(1, text.length() - 1).split(", ");
            return Arrays.stream(roles).map(Role::valueOf).collect(Collectors.toSet());
        }

        @Override
        public String print(Set<Role> roles, Locale locale) {
            return roles.toString();
        }
    }
}
