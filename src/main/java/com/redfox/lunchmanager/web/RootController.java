package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        var user = service.get(userId);
        SecurityUtil.setAuthUserId(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            return "redirect:admin/restaurants";
        } else {
            return "redirect:profile/restaurants";
        }
    }
}