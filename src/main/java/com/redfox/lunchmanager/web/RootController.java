package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.redfox.lunchmanager.util.Users.getTos;

@Controller
public class RootController {

    private final UserService service;

    public RootController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", getTos(service.getAll()));
        return "users";
    }

    @PostMapping("/users")
    public String setUser(@RequestParam String userId) {
        int id = Integer.parseInt(userId);
        var user = service.get(id);
        SecurityUtil.setAuthUserId(id);
        if (user.getRoles().contains(Role.ADMIN)) {
            return "redirect:/admin/restaurants";
        } else {
            return "redirect:/restaurants";
        }
    }
}