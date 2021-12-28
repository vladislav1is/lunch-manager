package com.redfox.lunchmanager.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redfox.lunchmanager.util.DateTimeUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId"),
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.registered DESC"),
        @NamedQuery(name = Dish.GET_BETWEEN, query = """
                    SELECT d FROM Dish d 
                    WHERE d.restaurant.id=:restaurantId AND d.registered >= :startDate AND d.registered < :endDate ORDER BY d.registered DESC
                """),
})
@Entity
@Table(name = "dishes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "registered", "restaurant_id"}, name = "dishes_idx")
})
public class Dish extends AbstractNamedEntity {

    public static final String DELETE = "Dish.delete";
    public static final String ALL_SORTED = "Dish.getAll";
    public static final String GET_BETWEEN = "Dish.getBetween";

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 10000)
    private long price;

    @Column(name = "registered", nullable = false, columnDefinition = "date default now()")
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate registered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //  https://stackoverflow.com/questions/31319358/jsonmanagedreference-vs-jsonbackreference
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {
    }

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
