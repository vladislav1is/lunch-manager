package com.redfox.lunchmanager;

import com.redfox.lunchmanager.model.Restaurant;

import java.util.List;

import static com.redfox.lunchmanager.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("dishes");

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

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5);

    public static Restaurant getNew() {
        return new Restaurant("Red");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant3);
        updated.setName("Green");
        return updated;
    }
}
