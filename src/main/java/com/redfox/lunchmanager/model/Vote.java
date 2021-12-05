package com.redfox.lunchmanager.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurantId"),
        @NamedQuery(name = Vote.BY_DATE, query = "SELECT v FROM Vote v WHERE v.registered=:voteDate AND v.user.id=:userId"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.registered DESC"),
        @NamedQuery(name = Vote.GET_BETWEEN, query = """
                    SELECT v FROM Vote v 
                    WHERE v.restaurant.id=:restaurantId AND v.registered >= :startDate AND v.registered < :endDate ORDER BY v.registered DESC
                """),
})
@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "registered"}, name = "votes_idx")
})
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String BY_DATE = "Vote.getByDate";
    public static final String ALL_SORTED = "Vote.getAll";
    public static final String GET_BETWEEN = "Vote.getBetween";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "registered", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate registered;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDate registered) {
        this(null, user, restaurant, registered);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate registered) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", registered=" + registered +
                '}';
    }
}
