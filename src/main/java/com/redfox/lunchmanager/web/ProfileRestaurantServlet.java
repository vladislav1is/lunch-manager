package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.web.restaurant.ProfileRestaurantController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileRestaurantServlet extends HttpServlet {

    private ProfileRestaurantController restaurantController;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        restaurantController = springContext.getBean(ProfileRestaurantController.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("restaurants", restaurantController.getAll());
        request.getRequestDispatcher("/profile-restaurants.jsp").forward(request, response);
    }
}
