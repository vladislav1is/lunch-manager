package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.MatcherFactory;
import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.to.RestaurantTo;

import java.util.List;

import static com.redfox.lunchmanager.web.dish.DishTestData.*;
import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final MatcherFactory.Matcher<Restaurant> WITH_DISHES_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            //  No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("dishes.restaurant").isEqualTo(e),
            (a, e) -> {
                throw new UnsupportedOperationException();
            });
    public static MatcherFactory.Matcher<RestaurantTo> TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT_ID_1 = START_SEQ + 4;
    public static final int RESTAURANT_ID_2 = START_SEQ + 5;
    public static final int RESTAURANT_ID_3 = START_SEQ + 6;
    public static final int RESTAURANT_ID_4 = START_SEQ + 7;
    public static final int RESTAURANT_ID_5 = START_SEQ + 8;
    public static final int NOT_FOUND = START_SEQ;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID_1, "August");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID_2, "September");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID_3, "October");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT_ID_4, "November");
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT_ID_5, "December");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant5, restaurant4, restaurant3, restaurant2);

    static {
        restaurant1.setDishes(List.of(dish1, dish2));
        restaurant2.setDishes(List.of(dish3, dish4));
        restaurant3.setDishes(List.of(dish5, dish6, dish7));
    }

    public static Restaurant getNew() {
        return new Restaurant("CreatedRestaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant3);
        updated.setName("UpdatedRestaurant");
        return updated;
    }
}
