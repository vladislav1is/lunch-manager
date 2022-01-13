package com.redfox.lunchmanager.to;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class RestaurantTo extends NamedTo {

    private final VoteTo voteTo;

    private final List<DishTo> dishes;

    @ConstructorProperties({"id", "name", "vote", "dishes"})
    //  https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html
    public RestaurantTo(Integer id, String name, VoteTo voteTo, List<DishTo> dishes) {
        super(id, name);
        this.voteTo = voteTo;
        this.dishes = dishes;
    }

    public VoteTo getVoteTo() {
        return voteTo;
    }

    public List<DishTo> getDishes() {
        return dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                Objects.equals(voteTo, that.voteTo) &&
                Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, voteTo, dishes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", voteTo=" + voteTo +
                ", dishes=" + dishes +
                '}';
    }
}
