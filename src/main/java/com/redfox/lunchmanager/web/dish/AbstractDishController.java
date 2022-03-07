package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.service.DishService;
import com.redfox.lunchmanager.to.DishTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Dishes.*;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractDishController {

    private static final Logger log = getLogger(AbstractDishController.class);

    @Autowired
    private DishService service;

    @Autowired
    private UniqueNameAndDateValidator uniqueNameAndDateValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(uniqueNameAndDateValidator);
    }

    public DishTo create(DishTo dishTo, int restaurantId) {
        Dish dish = convertToEntity(dishTo);
        log.info("create {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        service.create(dish, restaurantId);
        dishTo.setId(dish.id());
        return dishTo;
    }

    public void delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    public DishTo get(int id, int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return convertToDto(service.get(id, restaurantId));
    }

    public List<DishTo> getAll(int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return getTos(service.getAll(restaurantId));
    }

    public List<DishTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        log.info("getBetween ({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return getTos(service.getBetweenBy(startDate, endDate, restaurantId));
    }

    public void update(DishTo dishTo, int id, int restaurantId) {
        Dish dish = convertToEntity(dishTo);
        log.info("update {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }
}
