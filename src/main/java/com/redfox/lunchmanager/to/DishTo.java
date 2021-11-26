package com.redfox.lunchmanager.to;

import org.springframework.data.domain.Persistable;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class DishTo implements Persistable<Integer> {

    private Integer id;

    private final String name;

    private final long price;

    private final LocalDate registered;

    @ConstructorProperties({"id", "name", "price", "registered"})
    public DishTo(Integer id, String name, long price, LocalDate registered) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.registered = registered;
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

    public long getPrice() {
        return price;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return price == dishTo.price && Objects.equals(id, dishTo.id) && name.equals(dishTo.name) && registered.equals(dishTo.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, registered);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", registered=" + registered +
                '}';
    }
}
