package com.redfox.lunchmanager.to;

import com.redfox.lunchmanager.HasNameAndDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class DishTo extends NamedTo implements HasNameAndDate {

    @Min(value = 10)
    @Max(value = 10000)
    private final long price;

    @NotNull
    private LocalDate registered;

    @ConstructorProperties({"id", "name", "price", "registered"})
    public DishTo(Integer id, String name, long price, LocalDate registered) {
        super(id, name);
        this.price = price;
        this.registered = registered;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
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
