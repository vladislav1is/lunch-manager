package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.HasIdAndName;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import com.redfox.lunchmanager.web.ExceptionInfoHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UniqueNameValidator implements Validator {

    private final RestaurantRepository repository;

    public UniqueNameValidator(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndName.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndName restaurant = ((HasIdAndName) target);
        if (StringUtils.hasText(restaurant.getName())) {
            List<Restaurant> dbRestaurants = repository.getAll();
            Restaurant dbRestaurant = dbRestaurants.stream()
                    .filter(r -> restaurant.getName().equals(r.getName()))
                    .findFirst()
                    .orElse(null);
            if (dbRestaurant != null && !dbRestaurant.getId().equals(restaurant.getId())) {
                errors.rejectValue("name", ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME);
            }
        }
    }
}
