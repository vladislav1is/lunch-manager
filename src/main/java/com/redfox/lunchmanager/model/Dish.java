package com.redfox.lunchmanager.model;

import java.time.LocalDate;

public class Dish extends AbstractNamedEntity {

    private long price;
    private LocalDate registered;

    public Dish(String name, long price, LocalDate registered) {
        this(null, name, price, registered);
    }

    public Dish(Integer id, String name, long price, LocalDate registered) {
        super(id, name);
        this.price = price;
        this.registered = registered;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", registered=" + registered +
                '}';
    }
}
