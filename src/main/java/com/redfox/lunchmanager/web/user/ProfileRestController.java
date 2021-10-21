package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.model.User;
import org.springframework.stereotype.Controller;

import static com.redfox.lunchmanager.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}
