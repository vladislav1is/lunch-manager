package com.redfox.lunchmanager.to;

import org.springframework.data.domain.Persistable;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class RestaurantTo implements Persistable<Integer> {

    private Integer id;

    private final String name;

    private final VoteTo vote;

    private final List<DishTo> dishes;

    @ConstructorProperties({"id", "name", "vote", "dishes"})
    //  https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html
    public RestaurantTo(Integer id, String name, VoteTo vote, List<DishTo> dishes) {
        this.id = id;
        this.name = name;
        this.vote = vote;
        this.dishes = dishes;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public VoteTo getVote() {
        return vote;
    }

    public List<DishTo> getDishes() {
        return dishes;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                Objects.equals(vote, that.vote) &&
                Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vote, dishes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vote=" + vote +
                ", dishes=" + dishes +
                '}';
    }
}
