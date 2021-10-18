package com.redfox.lunchmanager.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {

    private User user;
    private Restaurant restaurant;
    private LocalDate voteDate;

    public Vote(User user, Restaurant restaurant, LocalDate voteDate) {
        this(null, user, restaurant, voteDate);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
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

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", voteDate=" + voteDate +
                '}';
    }
}
