package com.redfox.lunchmanager.web.dish;

import com.redfox.lunchmanager.MatcherFactory;
import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.to.DishTo;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDate.of;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingEqualsComparator(DishTo.class);

    public static final int YAKITORIYA_ID_1 = START_SEQ + 9;
    public static final int YAKITORIYA_ID_2 = START_SEQ + 10;
    public static final int DODO_PIZZA_ID_1 = START_SEQ + 11;
    public static final int DODO_PIZZA_ID_2 = START_SEQ + 12;
    public static final int MCDONALDS_ID_1 = START_SEQ + 13;
    public static final int MCDONALDS_ID_2 = START_SEQ + 14;
    public static final int MCDONALDS_ID_3 = START_SEQ + 15;
    public static final int NOT_FOUND = START_SEQ;

    public static final Dish yakitoriya_1 = new Dish(YAKITORIYA_ID_1, "Калифорния", 557_00, LocalDate.now());
    public static final Dish yakitoriya_2 = new Dish(YAKITORIYA_ID_2, "Ролл Лосось-карамель", 499_00, LocalDate.now());

    public static final Dish dodoPizza_1 = new Dish(DODO_PIZZA_ID_1, "Карбонара", 719_00, of(2021, Month.NOVEMBER, 11));
    public static final Dish dodoPizza_2 = new Dish(DODO_PIZZA_ID_2, "Пепперони", 649_00, of(2021, Month.NOVEMBER, 11));

    public static final Dish mcdonalds_1 = new Dish(MCDONALDS_ID_1, "Картофель Фри", 128_00, of(2021, Month.NOVEMBER, 11));
    public static final Dish mcdonalds_2 = new Dish(MCDONALDS_ID_2, "Двойной Чизбургер", 125_00, of(2021, Month.NOVEMBER, 11));
    public static final Dish mcdonalds_3 = new Dish(MCDONALDS_ID_3, "Чикенбургер", 53_00, of(2021, Month.NOVEMBER, 11));

    public static final List<Dish> dishes = List.of(yakitoriya_1, yakitoriya_2);

    public static Dish getNew() {
        return new Dish("CreatedDish", 300, LocalDate.of(2020, Month.NOVEMBER, 22));
    }

    public static Dish getUpdated() {
        return new Dish(DODO_PIZZA_ID_1, "UpdatedDish", 1000, dodoPizza_1.getRegistered().plus(2, ChronoUnit.DAYS));
    }
}
