package com.redfox.lunchmanager;

import com.redfox.lunchmanager.repository.inmemory.InMemoryDishRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryRestaurantRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryVoteRepository;
import com.redfox.lunchmanager.web.SecurityUtil;
import com.redfox.lunchmanager.web.dish.AdminDishController;
import com.redfox.lunchmanager.web.restaurant.AdminRestaurantController;
import com.redfox.lunchmanager.web.user.AdminRestController;
import com.redfox.lunchmanager.web.vote.VoteController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static com.redfox.lunchmanager.RestaurantTestData.restaurant1;
import static com.redfox.lunchmanager.UserTestData.user1;

/**
 * @see <a href="http://lunch-manager.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/vladislav1is/lunch-manager">Initial project</a>
 */
public class SpringMain {
    public static void main(String[] args) {
        System.out.println("Hello Java Enterprise!");
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            InMemoryUserRepository userRepository = appCtx.getBean(InMemoryUserRepository.class);
            userRepository.init();
            InMemoryRestaurantRepository restaurantRepository = appCtx.getBean(InMemoryRestaurantRepository.class);
            restaurantRepository.init();
            InMemoryDishRepository dishRepository = appCtx.getBean(InMemoryDishRepository.class);
            dishRepository.init();
            InMemoryVoteRepository voteRepository = appCtx.getBean(InMemoryVoteRepository.class);
            voteRepository.init();

            AdminRestController adminController = appCtx.getBean(AdminRestController.class);
            AdminRestaurantController restaurantController = appCtx.getBean(AdminRestaurantController.class);
            AdminDishController dishController = appCtx.getBean(AdminDishController.class);
            VoteController voteController = appCtx.getBean(VoteController.class);
            SecurityUtil.setAuthUserId(user1.getId());

            adminController.getAll().forEach(System.out::println);
            restaurantController.getAll().forEach(System.out::println);
            dishController.getAll(restaurant1.getId()).forEach(System.out::println);
            System.out.println(voteController.getByDate(LocalDate.now()));
        }
    }
}
