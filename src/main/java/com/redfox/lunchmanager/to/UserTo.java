package com.redfox.lunchmanager.to;

import com.redfox.lunchmanager.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class UserTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100, message = "length must be between 2 and 100 characters")
    private final String name;

    @NotBlank
    @Size(max = 100)
    @Email(message = "Email should be valid")
    private final String email;

    @NotBlank
    @Size(min = 5, max = 100, message = "length must be between 5 and 100 characters")
    private final String password;

    private Boolean enabled;

    private LocalDateTime registered;

    private Set<Role> roles;

    @ConstructorProperties({"id", "name", "email", "password", "enabled", "registered", "roles"})
    public UserTo(Integer id, String name, String email, String password, Boolean enabled, LocalDateTime registered, Set<Role> roles) {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
