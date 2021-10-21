package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.service.DishService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractDishController {

    private static final Logger log = getLogger(AbstractDishController.class);

    @Autowired
    private DishService service;

    public Dish create(Dish dish, int restaurantId) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        return service.create(dish, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    public Dish get(int id, int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }

    public List<Dish> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return service.getBetweenHalfOpen(startDate, endDate, restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        log.info("update {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }
}
