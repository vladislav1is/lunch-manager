package com.redfox.lunchmanager.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {

    private User user;
    private Restaurant restaurant;
    private LocalDate recordDate;

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate recordDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.recordDate = recordDate;
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

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", recordDate=" + recordDate +
                '}';
    }
}
