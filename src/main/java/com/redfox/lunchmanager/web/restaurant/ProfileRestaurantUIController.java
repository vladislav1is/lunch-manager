package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.to.RestaurantTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/profile/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantUIController extends AbstractRestaurantController {

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }
}