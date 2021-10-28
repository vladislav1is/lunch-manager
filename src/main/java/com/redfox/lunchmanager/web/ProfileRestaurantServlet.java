package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.web.restaurant.ProfileRestaurantController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileRestaurantServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private ProfileRestaurantController restaurantController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        restaurantController = springContext.getBean(ProfileRestaurantController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("restaurants", restaurantController.getAll());
        request.getRequestDispatcher("/profile-restaurants.jsp").forward(request, response);
    }
}
