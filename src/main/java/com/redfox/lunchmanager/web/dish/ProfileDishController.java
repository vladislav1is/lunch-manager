package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.model.Dish;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProfileDishController extends AbstractDishController {

    @Override
    public Dish get(int id, int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return super.getBetween(startDate, endDate, restaurantId);
    }
}
