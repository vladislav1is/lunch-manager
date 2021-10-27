package com.redfox.lunchmanager.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {

    private User user;
    private Restaurant restaurant;
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
