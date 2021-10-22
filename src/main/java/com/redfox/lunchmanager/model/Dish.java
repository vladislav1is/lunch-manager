package com.redfox.lunchmanager.model;

import java.time.LocalDate;

public class Dish extends AbstractNamedEntity {

    private long price;
    private LocalDate recordDate;

    public Dish(String name, long price, LocalDate recordDate) {
        this(null, name, price, recordDate);
    }

    public Dish(Integer id, String name, long price, LocalDate recordDate) {
        super(id, name);
        this.price = price;
        this.recordDate = recordDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", recordDate=" + recordDate +
                '}';
    }
}
