package com.redfox.lunchmanager.to;

import com.redfox.lunchmanager.model.Role;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class UserTo extends BaseTo {

    private final String name;

    private final String email;

    private final String password;

    private final boolean enabled;

    private final LocalDateTime registered;

    private final Set<Role> roles;

    @ConstructorProperties({"id", "name", "email", "password", "enabled", "registered", "roles"})
    public UserTo(Integer id, String name, String email, String password, boolean enabled, LocalDateTime registered, Set<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return enabled == userTo.enabled &&
                Objects.equals(id, userTo.id) &&
                name.equals(userTo.name) &&
                email.equals(userTo.email) &&
                password.equals(userTo.password) &&
                registered.equals(userTo.registered) &&
                roles.equals(userTo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, enabled, registered, roles);
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }
}
