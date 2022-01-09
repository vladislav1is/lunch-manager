package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.HasNameAndDate;
import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.redfox.lunchmanager.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME_AND_DATE;

@Component
public class UniqueNameAndDateValidator implements Validator {

    private final DishRepository repository;
    private final HttpServletRequest request;

    public UniqueNameAndDateValidator(DishRepository repository, @Nullable HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasNameAndDate.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasNameAndDate dish = ((HasNameAndDate) target);
        if (StringUtils.hasText(dish.getName()) && dish.getRegistered() != null) {
            String url = request.getRequestURL().toString();
            String[] urlComponents = url.split("/");
            int restaurantId = Integer.parseInt(urlComponents[6]);
            List<Dish> dbDishes = repository.getAll(restaurantId);
            Dish dbDish = dbDishes.stream()
                    .filter(d -> d.getName().equals(dish.getName()) && d.getRegistered().equals(dish.getRegistered()))
                    .findFirst()
                    .orElse(null);
            if (dbDish != null && !dbDish.getId().equals(dish.getId())) {
                errors.rejectValue("name", EXCEPTION_DUPLICATE_NAME_AND_DATE);
            }
        }
    }
}
