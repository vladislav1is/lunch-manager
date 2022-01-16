package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.service.RestaurantService;
import com.redfox.lunchmanager.service.VoteService;
import com.redfox.lunchmanager.to.RestaurantTo;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.util.List;

import static com.redfox.lunchmanager.util.Restaurants.*;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.assureIdConsistent;
import static com.redfox.lunchmanager.util.validation.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractRestaurantController {

    private static final Logger log = getLogger(AbstractRestaurantController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    public RestaurantTo create(RestaurantTo restaurantTo) {
        Restaurant restaurant = convertToEntity(restaurantTo);
        log.info("create {}", restaurant);
        checkNew(restaurant);
        restaurantService.create(restaurant);
        restaurantTo.setId(restaurant.id());
        return restaurantTo;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    public RestaurantTo get(int id) {
        log.info("get {}", id);
        Restaurant restaurant = restaurantService.get(id);
        return convertToDto(restaurant);
    }

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        Vote vote = voteService.getByDate(LocalDate.now(), SecurityUtil.authUserId());
        if (vote != null) {
            return getTos(restaurantService.getAll(), vote);
        } else {
            return getTos(restaurantService.getAll());
        }
    }

    public void update(RestaurantTo restaurantTo, int id) {
        Restaurant restaurant = convertToEntity(restaurantTo);
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    public RestaurantTo getWithDishesByDate(LocalDate localDate, int id) {
        log.info("getWithDishesByDate {} for restaurant {}", localDate, id);
        Restaurant restaurant = restaurantService.getWithDishesByDate(id, localDate);
        return convertToDto(restaurant, restaurant.getDishes());
    }
}
