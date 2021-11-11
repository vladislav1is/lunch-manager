package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.Profiles;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.web.restaurant.AdminRestaurantController;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AdminRestaurantServlet extends HttpServlet {

    private ClassPathXmlApplicationContext springContext;
    private AdminRestaurantController restaurantController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
//       springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        restaurantController = springContext.getBean(AdminRestaurantController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                restaurantController.delete(getId(request));
                response.sendRedirect("restaurants");
            }
            case "update", "create" -> {
                request.setAttribute("action", action);
                final var restaurant = "create".equals(action) ? new Restaurant("") :
                        restaurantController.get(getId(request));
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/restaurant-form.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute("restaurants", restaurantController.getAll());
                request.getRequestDispatcher("/admin-restaurants.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        var restaurant = new Restaurant(request.getParameter("title"));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            restaurantController.update(restaurant, getId(request));
        } else {
            restaurantController.create(restaurant);
        }
        response.sendRedirect("restaurants");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
