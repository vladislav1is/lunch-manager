package com.redfox.lunchmanager;

import com.redfox.lunchmanager.web.dish.AdminDishController;
import com.redfox.lunchmanager.web.restaurant.AdminRestaurantController;
import com.redfox.lunchmanager.web.user.AdminUserController;
import com.redfox.lunchmanager.web.vote.ProfileVoteController;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;

import static com.redfox.lunchmanager.web.restaurant.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.TestUtil.mockAuthorize;
import static com.redfox.lunchmanager.web.user.UserTestData.user1;
import static java.time.LocalDate.now;

/**
 * @see <a href="http://lunch-manager.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/vladislav1is/lunch-manager">Initial project</a>
 */
public class SpringMain {
    public static void main(String[] args) {
        System.out.println("Hello Java Enterprise!");
        // java 7 automatic resource management (ARM)
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/inmemory.xml");
            appCtx.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            var adminController = appCtx.getBean(AdminUserController.class);
            var restaurantController = appCtx.getBean(AdminRestaurantController.class);
            var dishController = appCtx.getBean(AdminDishController.class);
            var voteController = appCtx.getBean(ProfileVoteController.class);

            mockAuthorize(user1);

            adminController.getAll().forEach(System.out::println);
            restaurantController.getAll().forEach(System.out::println);
            dishController.getAll(restaurant1.id()).forEach(System.out::println);
            System.out.println(voteController.getByDate(now()));
        }
    }
}
