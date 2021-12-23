package com.redfox.lunchmanager.to;

import javax.validation.constraints.*;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100, message = "length must be between 2 and 100 characters")
    private final String name;

    @Min(value = 10, message = "should not be less than 10")
    @Max(value = 10000, message = "should not be greater than 10000")
    private final long price;

    @NotNull
    private final LocalDate registered;

    @ConstructorProperties({"id", "name", "price", "registered"})
    public DishTo(Integer id, String name, long price, LocalDate registered) {
        super(id);
        this.name = name;
        this.price = price;
        this.registered = registered;
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
