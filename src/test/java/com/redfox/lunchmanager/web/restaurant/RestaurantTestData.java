package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.MatcherFactory;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.to.RestaurantTo;

import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            //  No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("dishes.restaurant").isEqualTo(e),
            (a, e) -> {
                throw new UnsupportedOperationException();
            });
    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingAssertions(RestaurantTo.class,
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("voteTo").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("voteTo").isEqualTo(e));

    public static final int YAKITORIYA_ID = START_SEQ + 4;
    public static final int DODO_PIZZA_ID = START_SEQ + 5;
    public static final int MCDONALDS_ID = START_SEQ + 6;
    public static final int TEREMOK_ID = START_SEQ + 7;
    public static final int STARBUCKS_ID = START_SEQ + 8;
    public static final int NOT_FOUND = START_SEQ;

    public static final Restaurant yakitoriya = new Restaurant(YAKITORIYA_ID, "Якитория");
    public static final Restaurant dodoPizza = new Restaurant(DODO_PIZZA_ID, "Додо Пицца");
    public static final Restaurant mcdonalds = new Restaurant(MCDONALDS_ID, "McDonalds");
    public static final Restaurant teremok = new Restaurant(TEREMOK_ID, "Теремок");
    public static final Restaurant starbucks = new Restaurant(STARBUCKS_ID, "Starbucks");

    public static final List<Restaurant> restaurants = List.of(mcdonalds, starbucks, dodoPizza, teremok, yakitoriya);

    static {
        yakitoriya.setDishes(List.of(yakitoriya_1, yakitoriya_2));
        dodoPizza.setDishes(List.of(dodoPizza_1, dodoPizza_2));
        mcdonalds.setDishes(List.of(mcdonalds_1, mcdonalds_2, mcdonalds_3));
    }

    public static Restaurant getNew() {
        return new Restaurant("CreatedRestaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(mcdonalds);
        updated.setName("UpdatedRestaurant");
        return updated;
    }
}
