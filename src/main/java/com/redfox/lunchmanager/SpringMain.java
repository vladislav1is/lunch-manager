package com.redfox.lunchmanager;

import com.redfox.lunchmanager.repository.RestaurantRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryDishRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryRestaurantRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryUserRepository;
import com.redfox.lunchmanager.repository.inmemory.InMemoryVoteRepository;
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
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        InMemoryUserRepository userRepository = (InMemoryUserRepository) appCtx.getBean("inmemoryUserRepository");
        userRepository.init();
        InMemoryRestaurantRepository restaurantRepository = (InMemoryRestaurantRepository) appCtx.getBean(RestaurantRepository.class);
        restaurantRepository.init();
        InMemoryDishRepository dishRepository = appCtx.getBean(InMemoryDishRepository.class);
        dishRepository.init();
        InMemoryVoteRepository voteRepository = appCtx.getBean(InMemoryVoteRepository.class);
        voteRepository.init();

        System.out.println("InMemoryUserRepository");
        userRepository.getAll().forEach(System.out::println);
        System.out.println("InMemoryRestaurantRepository");
        restaurantRepository.getAll().forEach(System.out::println);
        System.out.println("InMemoryDishRepository");
        dishRepository.getAll(restaurant1.getId()).forEach(System.out::println);
        System.out.println("InMemoryVoteRepository");
        System.out.println(voteRepository.getByDate(LocalDate.now(), user1.getId()));
    }
}
