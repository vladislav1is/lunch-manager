package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.Role;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.web.user.AdminRestController;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);
    private ConfigurableApplicationContext springContext;
    private AdminRestController adminRestController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        adminRestController = springContext.getBean(AdminRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        User user = adminRestController.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            response.sendRedirect("admin/restaurants");
        } else {
            response.sendRedirect("profile/restaurants");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
