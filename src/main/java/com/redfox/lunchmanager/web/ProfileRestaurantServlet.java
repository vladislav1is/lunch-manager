package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.Profiles;
import com.redfox.lunchmanager.web.restaurant.ProfileRestaurantController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileRestaurantServlet extends HttpServlet {

    private ClassPathXmlApplicationContext springContext;
    private ProfileRestaurantController restaurantController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
//       springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
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
