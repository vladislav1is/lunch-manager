package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.AuthorizedUser;
import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.to.UserTo;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.EnumSet;

@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    @GetMapping
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("userTo", authUser.getUserTo());
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            return "profile";
        }
        super.update(userTo, authUser.getId());
        authUser.setTo(userTo);
        status.setComplete();
        return "redirect:/restaurants";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserTo userTo = new UserTo(null, null, null, null, Boolean.TRUE, LocalDateTime.now(), EnumSet.of(Role.USER));
        model.addAttribute("userTo", userTo);
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        super.create(userTo);
        status.setComplete();
        return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
    }
}
