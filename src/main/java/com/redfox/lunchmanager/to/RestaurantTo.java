package com.redfox.lunchmanager.to;

import org.springframework.data.domain.Persistable;

import java.beans.ConstructorProperties;
import java.util.List;

public class RestaurantTo implements Persistable<Integer> {

    private Integer id;

    private final String name;

    private List<DishTo> dishes;

    @ConstructorProperties({"id", "name"})
    //  https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html
    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
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
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
