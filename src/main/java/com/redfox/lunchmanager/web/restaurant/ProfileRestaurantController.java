package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.model.Restaurant;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProfileRestaurantController extends AbstractRestaurantController {

    @Override
    public Restaurant get(int id) {
        return super.get(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return super.getAll();
    }
}
