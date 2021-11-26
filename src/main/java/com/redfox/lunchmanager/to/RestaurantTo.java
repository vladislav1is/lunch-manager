package com.redfox.lunchmanager.to;

import org.springframework.data.domain.Persistable;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class RestaurantTo implements Persistable<Integer> {

    private Integer id;

    private final String name;

    private List<DishTo> dishes;

    @ConstructorProperties({"id", "name", "dishes"})
    //  https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html
    public RestaurantTo(Integer id, String name, List<DishTo> dishes) {
        this.id = id;
        this.name = name;
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

    public List<DishTo> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishTo> dishes) {
        this.dishes = dishes;
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
        return Objects.equals(id, that.id) && name.equals(that.name) && Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
