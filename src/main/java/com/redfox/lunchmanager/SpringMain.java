package com.redfox.lunchmanager;

import com.redfox.lunchmanager.repository.inmemory.InMemoryDishRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryRestaurantRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryVoteRepository;
import com.redfox.lunchmanager.service.DishService;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.UserService;
import com.redfox.lunchmanager.service.VoteService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static com.redfox.lunchmanager.util.Restaurants.restaurant1;
import static com.redfox.lunchmanager.util.Users.user1;

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

            UserService userService = appCtx.getBean(UserService.class);
            RestaurantService restaurantService = appCtx.getBean(RestaurantService.class);
            DishService dishService = appCtx.getBean(DishService.class);
            VoteService voteService = appCtx.getBean(VoteService.class);

            System.out.println("UserService");
            userService.getAll().forEach(System.out::println);
            System.out.println("RestaurantService");
            restaurantService.getAll().forEach(System.out::println);
            System.out.println("DishService");
            dishService.getAll(restaurant1.getId()).forEach(System.out::println);
            System.out.println("VoteService");
            System.out.println(voteService.getByDate(LocalDate.now(), user1.getId()));
        }
    }
}
