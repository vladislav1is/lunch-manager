package com.redfox.lunchmanager.web.converter;


import com.redfox.lunchmanager.model.Role;
import org.springframework.format.Formatter;

import java.util.Locale;

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
}
