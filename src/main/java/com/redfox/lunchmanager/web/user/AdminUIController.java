package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.to.UserTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping
    public List<UserTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Role role, @Valid UserTo userTo) {
        userTo.setRoles(role.equals(Role.USER) ? EnumSet.of(Role.USER) : EnumSet.of(Role.USER, Role.ADMIN));
        userTo.setRegistered(LocalDateTime.now());
        if (userTo.getId() == null) {
            userTo.setEnabled(Boolean.TRUE);
        }
        if (userTo.isNew()) {
            super.create(userTo);
        } else {
            super.update(userTo, userTo.id());
        }
    }

    @Override
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}
