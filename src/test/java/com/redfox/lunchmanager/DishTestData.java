package com.redfox.lunchmanager;

import com.redfox.lunchmanager.model.Dish;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDate.of;

public class DishTestData {
    public static final MatcherFactory<Dish> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID_1 = START_SEQ + 9;
    public static final int DISH_ID_2 = START_SEQ + 10;
    public static final int DISH_ID_3 = START_SEQ + 11;
    public static final int DISH_ID_4 = START_SEQ + 12;
    public static final int DISH_ID_5 = START_SEQ + 13;
    public static final int DISH_ID_6 = START_SEQ + 14;
    public static final int DISH_ID_7 = START_SEQ + 15;
    public static final int NOT_FOUND = START_SEQ;

    public static final Dish dish1 = new Dish(DISH_ID_1, "roast pork", 250, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish2 = new Dish(DISH_ID_2, "fish and chips", 350, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish3 = new Dish(DISH_ID_3, "roast vegetables", 130, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish4 = new Dish(DISH_ID_4, "roast turkey", 210, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish5 = new Dish(DISH_ID_5, "tomato soup", 150, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish6 = new Dish(DISH_ID_6, "pizza", 180, of(2021, Month.NOVEMBER, 11));
    public static final Dish dish7 = new Dish(DISH_ID_7, "pasta", 175, of(2021, Month.NOVEMBER, 11));

    public static final List<Dish> dishes = List.of(dish1, dish2);

    public static Dish getNew() {
        return new Dish("newDish", 300, LocalDate.of(2020, Month.NOVEMBER, 22));
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID_3, "updateDish", 1000, dish3.getRegistered().plus(2, ChronoUnit.DAYS));
    }
}
